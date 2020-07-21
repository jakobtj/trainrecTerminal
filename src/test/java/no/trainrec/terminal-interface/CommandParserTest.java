package no.trainrec.terminal_interface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.mockito.Mockito;

public class CommandParserTest {
    CommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CommandParser("");
    }

    @Test
    public void testParseEmptyString() {
        parser.parse("");

        Assertions.assertEquals("", parser.getCommand());
        Assertions.assertEquals("", parser.getArgument());
    }

    @Test
    public void testCommandNoArgument() {
        parser.parse("add");

        Assertions.assertEquals("add", parser.getCommand());
        Assertions.assertEquals("", parser.getArgument());
    }

    @Test
    public void testCommandWithArgument() {
        parser.parse("add Squat");

        Assertions.assertEquals("add", parser.getCommand());
        Assertions.assertEquals("Squat", parser.getArgument());
    }

    @Test
    public void testCommandWithMultiWordArgument() {
        parser.parse("add Bench press");

        Assertions.assertEquals("add", parser.getCommand());
        Assertions.assertEquals("Bench press", parser.getArgument());
    }
}
