package no.trainrec.terminal_interface;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
    private JCommander jc;
    private Map<String, JCommanderMethod> methods;

    public CommandLineParser() {
        rec = new TrainingRecord(new CSVStorage());
        adder = new EntryAdder(rec);
        methods = createMethodMap();
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

    private Map<String, JCommanderMethod> createMethodMap() {
        Map<String, JCommanderMethod> map = new HashMap<String, JCommanderMethod>();
        map.put("date", new SetDate());
        map.put("add", new AddEntry());
        map.put("list", new ListEntries());
        return map;
    }

    private void linkCommandsAndCommander() {
        for (String key : methods.keySet()) {
            jc.addCommand(key, methods.get(key));
        }
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
            String date = methods.get("date").pop();
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
            String exercise = methods.get("add").pop();
            adder.addEntry(exercise);
            System.out.println(String.format("%s added", exercise));
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            // Ignore
        }
    }

    private void resolveListCommand() {
        for (ExerciseEntry entry : rec.listEntries()) {
            System.out.println(String.format(
                        "%s %s", entry.getDate(), entry.getExercise()
                        ));
        }
    }
}

class JCommanderMethod {
    @Parameter
    List<String> argv;

    public String pop() {
        String res = argv.get(0);
        argv.clear();
        return res;
    }
}

@Parameters(commandDescription = "Sets date")
class SetDate extends JCommanderMethod {}

@Parameters(commandDescription = "Adds exercise entry")
class AddEntry extends JCommanderMethod {
    @Override
    public String pop() {
        String res = argv.get(0);
        if (argv.size() > 1) {
            res = String.join(" ", argv);
        }
        argv.clear();
        return res;
    }
}

@Parameters(commandDescription = "List all exercise entries")
class ListEntries extends JCommanderMethod {}
