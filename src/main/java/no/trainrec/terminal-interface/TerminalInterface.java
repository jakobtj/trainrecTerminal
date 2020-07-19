package no.trainrec.terminal_interface;

import java.util.Scanner;
import java.util.List;

public class TerminalInterface {
    private CoreAccessor core;

    public TerminalInterface(CoreAccessor inputCore) {
        core = inputCore;
    }

    public static void main(String[] argv) {
        CSVStorage storage = new CSVStorage();
        TerminalInterface ui = new TerminalInterface(new CoreAccessor(storage));
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
            }
        }
    }

    public void execute(String command, String argument) {
        switch (command) {
            case "list": printEntryList(); break;
            case "add": core.addEntry(argument); break;
            case "date": resolveDateCommand(argument); break;
            default: System.out.println(String.format(
                                 "%s is not a valid command", command
                                 )); break;
        }
    }

    private void printEntryList() {
        List<String> entries = core.listEntries();
        for (String entry : entries) {
            System.out.println(entry);
        }
    }

    private void resolveDateCommand(String argument) {
        if (argument == "") {
            System.out.println(String.format(
                        "Active date is %s", core.getActiveDate()
                        ));
        } else {
            try {
                core.setActiveDate(argument);
                System.out.println(String.format(
                        "Active date is set to %s", argument
                        ));
            } catch (IllegalArgumentException ex) {
                System.out.println("Date must be given as YYYY-MM-DD");
            }
        }
    }
}
