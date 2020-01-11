package trainrec;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class TerminalInterfaceTest {
    private ByteArrayOutputStream streamRedirect;
    private PrintStream oldStream;

    @Before
    public void setUp() {
        // Create a stream to redirect output to String
        streamRedirect = new ByteArrayOutputStream();
        oldStream = System.out; // Keep old system out
        System.setOut(new PrintStream(streamRedirect));
    }

    @Test
    public void testStartupMessage() {
        TerminalInterface ui = new TerminalInterface();
        ui.startup();

        String expected = "Current date is 2020-01-10\n"
            + "Enter 'help' for command overview\n";
        Assert.assertEquals(expected, streamRedirect.toString());
   }

    @After
    public void tearDown() {
        System.out.flush();
        System.setOut(oldStream);
    }
}
