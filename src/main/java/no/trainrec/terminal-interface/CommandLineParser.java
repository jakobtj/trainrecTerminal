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

    public CommandLineParser() {
        rec = new TrainingRecord();
    }

    public void parse(String[] argv) {
        SetDate setDate = new SetDate();
        AddEntry addEntry = new AddEntry();
        ListEntries listEntries = new ListEntries();

        JCommander jct = new JCommander();
        jct.addCommand("date", setDate);
        jct.addCommand("add", addEntry);
        jct.addCommand("list", listEntries);

        try {
            jct.parse(argv);
            if ("date".equals(jct.getParsedCommand())) {
                String date = setDate.get();
                try {
                    rec.setActiveDate(EntryDate.fromString(date));
                    System.out.println(String.format("Date is set to %s", date));
                } catch (IllegalArgumentException ex) {
                    System.out.println(String.format(
                                "%s is not recognized as date", date
                                ));
                }
            } else if ("add".equals(jct.getParsedCommand())) {
                String exercise = addEntry.get();
                rec.addEntry(exercise);
                System.out.println(String.format("%s added", exercise));
            } else if ("list".equals(jct.getParsedCommand())) {
                List<ExerciseEntry> entries = rec.listEntries();
                for (ExerciseEntry entry : entries) {
                    System.out.println(String.format(
                                "%s %s", entry.getDate(), entry.getExercise()
                                ));
                }
            } else {
                System.out.println("Unknown option");
            }
        } catch (ParameterException ex) {
            System.out.println(ex.getMessage());
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
