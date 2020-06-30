package no.trainrec.terminal_interface;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TerminalInterfaceTest {
    private ByteArrayOutputStream streamRedirect;
    private PrintStream oldStream;
    private TerminalInterface ui;

    @Before
    public void setUp() {
        // Create a stream to redirect output to String
        streamRedirect = new ByteArrayOutputStream();
        oldStream = System.out; // Keep old system out
        System.setOut(new PrintStream(streamRedirect));
        
        ui = new TerminalInterface();
    }

    @Test
    public void testNewInterfaceListsEmpty() {
        ui.parse("list");

        Assert.assertEquals("", streamRedirect.toString());
    }

    @Test
    public void testSetDate() {
        ui.parse("date 2020-01-10");

        Assert.assertEquals("Date is set to 2020-01-10\n",
                streamRedirect.toString());
    }

    @Test
    public void testSetDateWrongFormatReturnErrorMessage() {
        ui.parse("date %%");

        Assert.assertEquals("%% is not recognized as date\n",
                streamRedirect.toString());
    }

    @Test
    public void testSetDateMultipleTimes() {
        ui.parse("date %%");
        ui.parse("date ??");

        Assert.assertEquals("%% is not recognized as date\n"
                + "?? is not recognized as date\n",
                streamRedirect.toString()
                );
    }

    @Test
    public void testDateKeywordNoArguments() {
        ui.parse("date");
        ui.parse("date 2020-01-10");
        ui.parse("date");
        String today = LocalDate.now().format(
                DateTimeFormatter.ISO_LOCAL_DATE
                );
        String expected = String.format("Active date is %s\n", today)
            + "Date is set to 2020-01-10\n"
            + "Active date is 2020-01-10\n";

        Assert.assertEquals(expected, streamRedirect.toString());
    }

    @Test
    public void testAddEntry() {
        ui.parse("date 2020-01-10");
        ui.parse("add Squat");
        ui.parse("list");

        String expected = "Date is set to 2020-01-10\n"
            + "Squat added\n" 
            + "2020-01-10 Squat\n";
        Assert.assertEquals(expected, streamRedirect.toString());
   }

    @Test
    public void testAddMultipleEntries() {
        ui.parse("add Squat");
        ui.parse("add Bench press");

        String expected = "Squat added\n" + "Bench press added\n";
        Assert.assertEquals(expected, streamRedirect.toString());
   }

    @Test
    public void testAddKeywordNoArguments() {
        ui.parse("add");
        ui.parse("add Squat");
        ui.parse("add");

        String expected = "Squat added\n";
        Assert.assertEquals(expected, streamRedirect.toString());
   }

    @After
    public void tearDown() {
        System.out.flush();
        System.setOut(oldStream);
    }
}
