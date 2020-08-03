package no.trainrec.terminal_interface;

import java.lang.ArrayIndexOutOfBoundsException;

public class CommandParser {
    private String command;
    private String argument; 

    public CommandParser(String inputString) {
        parse(inputString);
    }

    public String getCommand() {
        return command;
    }
    
    public String getArgument() {
        return argument;
    }

    public void parse(String inputString) {
        String[] input = inputString.split(" ");
        command = getFirst(input);
        if (input.length > 1) {
            argument = joinRemainder(input, 1);
        } else {
            argument = "";
        }
    }

    private String getFirst(String[] input) {
        try {
            return input[0];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return  "";
        }
    }

    private String joinRemainder(String[] input, int startIndex) {
        String[] argArray = new String[input.length - startIndex];
        System.arraycopy(
                input, startIndex, argArray, 0, input.length - startIndex
                );
        return String.join(" ", argArray);
    }
}
