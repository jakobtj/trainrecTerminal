package no.trainrec.terminal_interface;

import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import no.trainrec.core.TrainingRecord;
import no.trainrec.core.ExerciseEntry;
import no.trainrec.core.EntryDate;

public class CommandLineParser {
    private TrainingRecord rec;
    private SetDate setDate;
    private AddEntry addEntry;
    private ListEntries listEntries;
    private JCommander jc;

    public CommandLineParser() {
        rec = new TrainingRecord();
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
        if ("date".equals(jc.getParsedCommand())) {
            resolveDateCommand(setDate.get());
        } else if ("add".equals(jc.getParsedCommand())) {
            resolveAddCommand(addEntry.get());
        } else if ("list".equals(jc.getParsedCommand())) {
            resolveListCommand(rec.listEntries());
        } else {
            System.out.println("Unknown option");
        }
    }

    private void resolveDateCommand(String date) {
        try {
            rec.setActiveDate(EntryDate.fromString(date));
            System.out.println(String.format("Date is set to %s", date));
        } catch (IllegalArgumentException ex) {
            System.out.println(String.format(
                        "%s is not recognized as date", date
                        ));
        }
    }

    private void resolveAddCommand(String exercise) {
        rec.addEntry(exercise);
        System.out.println(String.format("%s added", exercise));
    }

    private void resolveListCommand(List<ExerciseEntry> entries) {
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

    public String get() {
        return date.get(0);
    }
}

@Parameters(commandDescription = "Adds exercise entry")
class AddEntry {
    @Parameter(description = "Name of exercise")
    private List<String> exercise;

    public String get() {
        return exercise.get(0);
    }
}

@Parameters(commandDescription = "List all exercise entries")
class ListEntries {
    @Parameter
    private List argv;
}
