package no.trainrec.terminal_interface;

import no.trainrec.storage.CSV;
import no.trainrec.storage.FileIO;

import java.util.Scanner;

import java.io.File;

public class CommandLineMain {
    public static void main(String[] argv) {
        try {
            File data = new File("data.csv");
            data.createNewFile(); // Does nothing if file already exists
            FileIO io = new FileIO(data);
            CommandLineMain.run(io);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void run(FileIO io) {
        CSV storage = new CSV(io);
        TextInterface ui = new TextInterface(new CoreAccessor(storage));
        CommandParser parser = new CommandParser("");
        Scanner input = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.print(">");
            parser.parse(input.nextLine());
            if (parser.getCommand().equals("exit")) {
                ui.execute("save", "");
                run = false;
            }
            if (!parser.getCommand().isBlank()) {
                ui.execute(parser.getCommand(), parser.getArgument());
                System.out.println(ui.getResponse());
            }
        }
    }
}
