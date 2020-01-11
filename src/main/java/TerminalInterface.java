package trainrec;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class TerminalInterface {
    private LocalDate date;

    public TerminalInterface() {
        date = LocalDate.of(2020, 1, 10);
    }

    public void startup() {
        System.out.println("Current date is " + getDateAsString());
        System.out.println("Enter 'help' for command overview");
    }

    private String getDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return date.format(formatter);
    }
}
