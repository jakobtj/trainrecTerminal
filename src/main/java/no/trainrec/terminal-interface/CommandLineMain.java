package no.trainrec.terminal_interface;

import java.util.Scanner;

public class CommandLineMain {
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
}
