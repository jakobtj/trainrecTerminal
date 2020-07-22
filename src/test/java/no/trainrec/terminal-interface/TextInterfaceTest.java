package no.trainrec.terminal_interface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.mockito.Mockito;

import java.util.List;
import java.util.ArrayList;

public class TextInterfaceTest {
    private TextInterface ui;
    private CoreAccessor core;

    @BeforeEach
    public void setUp() {
        core = Mockito.mock(CoreAccessor.class);
        ui = new TextInterface(core);
    }

    @Test
    public void testInvalidCommand() {
        ui.execute("??", "");

        Assertions.assertEquals("?? is not a valid command", ui.getResponse());
    }

    @Test
    public void testGetResponseBeforeCommand() {
        Assertions.assertEquals("", ui.getResponse());
    }

    @Test
    public void testNewInterfaceListsEmpty() {
        Mockito.when(core.listEntries()).thenReturn(new ArrayList<String>());

        ui.execute("list", "");

        Assertions.assertEquals("", ui.getResponse());
    }

    @Test
    public void testList() {
        List<String> entries = new ArrayList<String>();
        entries.add("2020-10-10 Squat");
        entries.add("2020-11-10 Bench press");
        Mockito.when(core.listEntries()).thenReturn(entries);

        ui.execute("list", "");

        Assertions.assertEquals("2020-10-10 Squat\n2020-11-10 Bench press",
                ui.getResponse()
                );
    }

    @Test
    public void testAdd() {
        ui.execute("add", "Squat");

        Mockito.verify(core).addEntry("Squat");
        Assertions.assertEquals("Squat added", ui.getResponse());
    }

    @Test
    public void testDateInvalidArgument() {
        Mockito.doThrow(IllegalArgumentException.class).when(core
                ).setActiveDate("??");
        ui.execute("date", "??");

        Assertions.assertEquals("Date must be given as YYYY-MM-DD", 
                ui.getResponse()
                );
    }

    @Test
    public void testSetActiveDate() {
        ui.execute("date", "2020-10-10");

        Mockito.verify(core).setActiveDate("2020-10-10");
        Assertions.assertEquals("Active date is set to 2020-10-10",
                ui.getResponse()
                );
    }

    @Test
    public void testDateCommandWithoutArgumentGetsDate() {
        Mockito.when(core.getActiveDate()).thenReturn("2020-10-10");

        ui.execute("date", "");

        Assertions.assertEquals("Active date is 2020-10-10", ui.getResponse());
    }

    @Test
    public void testSave() {
        ui.execute("save", "");

        Mockito.verify(core).save();
        Assertions.assertEquals("Record saved", ui.getResponse());
    }
}
