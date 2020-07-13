package no.trainrec.terminal_interface;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import org.mockito.Mockito;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.ArrayList;

public class TerminalInterfaceTest {
    private ByteArrayOutputStream streamRedirect;
    private PrintStream oldStream;
    private TerminalInterface ui;
    private CoreAccessor core;

    @Before
    public void setUp() {
        // Create a stream to redirect output to String
        streamRedirect = new ByteArrayOutputStream();
        oldStream = System.out; // Keep old system out
        System.setOut(new PrintStream(streamRedirect));
        
        core = Mockito.mock(CoreAccessor.class);
        ui = new TerminalInterface(core);
    }

    @Test
    public void testInvalidCommand() {
        ui.execute("??", "");

        Assert.assertEquals("?? is not a valid command\n",
                streamRedirect.toString()
                );
    }

    @Test
    public void testNewInterfaceListsEmpty() {
        ui.execute("list", "");

        Mockito.verify(core).listEntries();
        Assert.assertEquals("", streamRedirect.toString());
    }

    @Test
    public void testList() {
        List<String> entries = new ArrayList<String>();
        entries.add("2020-10-10 Squat");
        entries.add("2020-11-10 Bench press");
        Mockito.when(core.listEntries()).thenReturn(entries);

        ui.execute("list", "");

        Assert.assertEquals("2020-10-10 Squat\n2020-11-10 Bench press\n",
                streamRedirect.toString()
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

        Assert.assertEquals("Date must be given as YYYY-MM-DD\n",
                streamRedirect.toString());
    }

    @Test
    public void testSetActiveDate() {
        ui.execute("date", "2020-10-10");

        Mockito.verify(core).setActiveDate("2020-10-10");
        Assert.assertEquals("Active date is set to 2020-10-10\n",
                streamRedirect.toString()
                );
    }

    @Test
    public void testDateCommandWithoutArgumentGetsDate() {
        Mockito.when(core.getActiveDate()).thenReturn("2020-10-10");

        ui.execute("date", "");

        Mockito.verify(core).getActiveDate();
        Assert.assertEquals("Active date is 2020-10-10\n",
                streamRedirect.toString()
                );
    }

    @After
    public void tearDown() {
        System.out.flush();
        System.setOut(oldStream);
    }
}
