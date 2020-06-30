package no.trainrec.terminal_interface;

import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import no.trainrec.core.TrainingRecord;
import no.trainrec.core.EntryAdder;
import no.trainrec.core.ExerciseEntry;

public class CommandLineParser {
    private TrainingRecord rec;
    private EntryAdder adder;
    private SetDate setDate;
    private AddEntry addEntry;
    private ListEntries listEntries;
    private JCommander jc;

    public CommandLineParser() {
        rec = new TrainingRecord(new CSVStorage());
        adder = new EntryAdder(rec);
        setDate = new SetDate();
        addEntry = new AddEntry();
        listEntries = new ListEntries();
        jc = new JCommander();
        linkCommandsAndCommander();
    }

    public void parse(String[] argv) {
        try {
            jc.parse(argv);
            resolveCommand();
        } catch (ParameterException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void linkCommandsAndCommander() {
        jc.addCommand("date", setDate);
        jc.addCommand("add", addEntry);
        jc.addCommand("list", listEntries);
    }

    private void resolveCommand() {
        switch (jc.getParsedCommand()) {
            case "date": resolveDateCommand(); break;
            case "add": resolveAddCommand(); break;
            case "list": resolveListCommand(); break;
            default: System.out.println("Unknown option"); break;
        }
   }

    private void resolveDateCommand() {
        try {
            String date = setDate.pop();
            try { 
                adder.setActiveDate(date);
                System.out.println(String.format("Date is set to %s", date));
            } catch (IllegalArgumentException ex) {
                System.out.println(String.format(
                            "%s is not recognized as date", date
                            ));
            }
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println(String.format("Active date is %s",
                        adder.getActiveDate()
                        ));
        }
    }

    private void resolveAddCommand() {
        try {
            String exercise = addEntry.pop();
            adder.addEntry(exercise);
            System.out.println(String.format("%s added", exercise));
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            // Ignore
        }
    }

    private void resolveListCommand() {
        List<ExerciseEntry> entries = rec.listEntries();
        for (ExerciseEntry entry : entries) {
            System.out.println(String.format(
                        "%s %s", entry.getDate(), entry.getExercise()
                        ));
        }
    }
}

@Parameters(commandDescription = "Sets date")
class SetDate {
    @Parameter(description = "ISO-formatted date string to set")
    private List<String> date;

    public String pop() {
        String res = date.get(0);
        date.clear();
        return res;
    }
}

@Parameters(commandDescription = "Adds exercise entry")
class AddEntry {
    @Parameter(description = "Name of exercise")
    private List<String> exercise;

    public String pop() {
        String res = exercise.get(0);
        if (exercise.size() > 1) {
            res = String.join(" ", exercise);
        }
        exercise.clear();
        return res;
    }
}

@Parameters(commandDescription = "List all exercise entries")
class ListEntries {
    @Parameter
    private List argv;
}
