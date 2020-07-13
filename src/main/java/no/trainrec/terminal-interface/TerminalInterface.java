package no.trainrec.terminal_interface;

import java.util.Scanner;
import java.util.List;

public class TerminalInterface {
    private CoreAccessor core;

    public TerminalInterface(CoreAccessor inputCore) {
        core = inputCore;
    }

    public static void main(String[] argv) {
        TerminalInterface ui = new TerminalInterface(new CoreAccessor());
        CommandParser parser = new CommandParser("");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            parser.parse(input.nextLine());
            ui.execute(parser.getCommand(), parser.getArgument());
        }
    }

    public void execute(String command, String argument) {
        switch (command) {
            case "list": printEntryList(); break;
            case "add": core.addEntry(argument); break;
            case "date": resolveDateCommand(argument); break;
            default: System.out.print(String.format(
                                 "%s is not a valid command", command
                                 )); break;
        }
    }

    private void printEntryList() {
        List<String> entries = core.listEntries();
        for (String entry : entries) {
            System.out.print(entry + "\n");
        }
    }

    private void resolveDateCommand(String argument) {
        if (argument == "") {
            System.out.print(String.format(
                        "Active date is %s", core.getActiveDate()
                        ));
        } else {
            try {
                core.setActiveDate(argument);
                System.out.print(String.format(
                        "Active date is set to %s", argument
                        ));
            } catch (IllegalArgumentException ex) {
                System.out.print("Date must be given as YYYY-MM-DD");
            }
        }
    }
}
