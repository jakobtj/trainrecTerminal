package no.trainrec.terminal_interface;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.mockito.Mockito;

public class CommandParserTest {
    CommandParser parser;

    @Before
    public void setUp() {
        parser = new CommandParser("");
    }

    @Test
    public void testParseEmptyString() {
        parser.parse("");

        Assert.assertEquals("", parser.getCommand());
        Assert.assertEquals("", parser.getArgument());
    }

    @Test
    public void testCommandNoArgument() {
        parser.parse("add");

        Assert.assertEquals("add", parser.getCommand());
        Assert.assertEquals("", parser.getArgument());
    }

    @Test
    public void testCommandWithArgument() {
        parser.parse("add Squat");

        Assert.assertEquals("add", parser.getCommand());
        Assert.assertEquals("Squat", parser.getArgument());
    }

    @Test
    public void testCommandWithMultiWordArgument() {
        parser.parse("add Bench press");

        Assert.assertEquals("add", parser.getCommand());
        Assert.assertEquals("Bench press", parser.getArgument());
    }
}
