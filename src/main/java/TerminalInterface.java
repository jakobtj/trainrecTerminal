package trainrecTerminal;

import java.util.Scanner;

public class TerminalInterface {
    private CommandLineParser parser;

    public TerminalInterface() {
        parser = new CommandLineParser();
    }

    public static void main(String[] argv) {
        TerminalInterface ui = new TerminalInterface();
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String line = input.nextLine();
            if (line.equals("exit")) {
                break;
            } else if (!line.isEmpty()){
                ui.parse(line);
            }
        }
    }

    public void parse(String userInput) {
        String[] argv = userInput.split(" ");
        parser.parse(argv);
    }
}
