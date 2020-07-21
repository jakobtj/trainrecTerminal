package no.trainrec.terminal_interface;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.mockito.Mockito;

import java.util.List;
import java.util.ArrayList;

public class TextInterfaceTest {
    private TextInterface ui;
    private CoreAccessor core;

    @Before
    public void setUp() {
        core = Mockito.mock(CoreAccessor.class);
        ui = new TextInterface(core);
    }

    @Test
    public void testInvalidCommand() {
        ui.execute("??", "");

        Assert.assertEquals("?? is not a valid command", ui.getResponse());
    }

    @Test
    public void testNewInterfaceListsEmpty() {
        Mockito.when(core.listEntries()).thenReturn(new ArrayList<String>());

        ui.execute("list", "");

        Assert.assertEquals("", ui.getResponse());
    }

    @Test
    public void testList() {
        List<String> entries = new ArrayList<String>();
        entries.add("2020-10-10 Squat");
        entries.add("2020-11-10 Bench press");
        Mockito.when(core.listEntries()).thenReturn(entries);

        ui.execute("list", "");

        Assert.assertEquals("2020-10-10 Squat\n2020-11-10 Bench press",
                ui.getResponse()
                );
    }

    @Test
    public void testAdd() {
        ui.execute("add", "Squat");

        Mockito.verify(core).addEntry("Squat");
    }

    @Test
    public void testDateInvalidArgument() {
        Mockito.doThrow(IllegalArgumentException.class).when(core
                ).setActiveDate("??");
        ui.execute("date", "??");

        Assert.assertEquals("Date must be given as YYYY-MM-DD", 
                ui.getResponse()
                );
    }

    @Test
    public void testSetActiveDate() {
        ui.execute("date", "2020-10-10");

        Mockito.verify(core).setActiveDate("2020-10-10");
        Assert.assertEquals("Active date is set to 2020-10-10",
                ui.getResponse()
                );
    }

    @Test
    public void testDateCommandWithoutArgumentGetsDate() {
        Mockito.when(core.getActiveDate()).thenReturn("2020-10-10");

        ui.execute("date", "");

        Assert.assertEquals("Active date is 2020-10-10", ui.getResponse());
    }
}
