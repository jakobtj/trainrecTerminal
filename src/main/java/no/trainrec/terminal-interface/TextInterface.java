package no.trainrec.terminal_interface;

import java.util.Scanner;
import java.util.List;

public class TextInterface {
    private CoreAccessor core;
    private String response;

    public TextInterface(CoreAccessor inputCore) {
        core = inputCore;
    }

    public static void main(String[] argv) {
        CSVStorage storage = new CSVStorage();
        TextInterface ui = new TextInterface(new CoreAccessor(storage));
        CommandParser parser = new CommandParser("");
        Scanner input = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.print(">");
            parser.parse(input.nextLine());
            if (parser.getCommand().equals("exit")) {
                run = false;
            } else {
                ui.execute(parser.getCommand(), parser.getArgument());
                System.out.println(ui.getResponse());
            }
        }
    }

    public void execute(String command, String argument) {
        switch (command) {
            case "list": printEntryList(); break;
            case "add": core.addEntry(argument); break;
            case "date": resolveDateCommand(argument); break;
            default: response = String.format(
                                 "%s is not a valid command", command
                                 ); break;
        }
    }

    public String getResponse() {
        return response;
    }

    private void printEntryList() {
        response = String.join("\n", core.listEntries());
    }

    private void resolveDateCommand(String argument) {
        if (argument == "") {
            response = String.format(
                        "Active date is %s", core.getActiveDate()
                        );
        } else {
            try {
                core.setActiveDate(argument);
                response = String.format(
                        "Active date is set to %s", argument
                        );
            } catch (IllegalArgumentException ex) {
                response = "Date must be given as YYYY-MM-DD";
            }
        }
    }
}
