package no.trainrec.terminal_interface;

import java.util.List;

import no.trainrec.core.TrainingRecord;
import no.trainrec.core.EntryAdder;
import no.trainrec.core.ExerciseEntry;

public class CoreAccessor {
    private TrainingRecord rec;
    private EntryAdder adder; 

    public CoreAccessor(TrainingRecord inputRec, EntryAdder inputAdder) {
        rec = inputRec;
        adder = inputAdder;
    }

    public void addEntry(String entryName) {
        adder.addEntry(entryName);
    }

    public String listEntries() {
        String entries = "";
        for (ExerciseEntry entry : rec.listEntries()) {
            entries = String.format(
                    "%s\n%s %s", entries, entry.getDate(), entry.getExercise()
                    );
        }
        return entries;
    }
}
