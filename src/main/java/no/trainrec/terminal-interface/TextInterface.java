package no.trainrec.terminal_interface;

import java.util.List;

public class TextInterface {
    private CoreAccessor core;
    private String response = "";

    public TextInterface(CoreAccessor inputCore) {
        core = inputCore;
    }

    public void execute(String command, String argument) {
        switch (command) {
            case "list": printEntryList(); break;
            case "add": addEntry(argument); break;
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

    private void addEntry(String argument) {
        core.addEntry(argument);
        response = String.format("%s added", argument);
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
