package no.trainrec.terminal_interface;

import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class CommandLineMain {
    public static void main(String[] argv) {
        try {
            File data = new File("data.csv");
            data.createNewFile(); // Does nothing if file already exists
            BufferedReader reader = new BufferedReader(new FileReader(data));
            CommandLineMain.run(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void run(BufferedReader reader) {
        CSVStorage storage = new CSVStorage(reader);
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
