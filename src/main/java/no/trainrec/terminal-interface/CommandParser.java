package no.trainrec.terminal_interface;

public class CommandParser {
    private String command;
    private String argument; 

    public CommandParser(String inputString) {
        parse(inputString);
    }

    public void parse(String inputString) {
        String[] input = inputString.split(" ");
        command = input[0];
        if (input.length > 1) {
            String[] argArray = new String[input.length - 1];
            System.arraycopy(input, 1, argArray, 0, input.length - 1);
            argument = String.join(" ", argArray);
        } else {
            argument = "";
        }
    }

    public String getCommand() {
        return command;
    }
    
    public String getArgument() {
        return argument;
    }
}
