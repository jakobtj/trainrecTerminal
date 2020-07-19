package no.trainrec.terminal_interface;

import java.util.List;
import java.util.ArrayList;

import no.trainrec.core.data.TrainingRecord;
import no.trainrec.core.data.StorageInterface;
import no.trainrec.core.use_case.EntryAdder;
import no.trainrec.core.domain.ExerciseEntry;

public class CoreAccessor {
    private TrainingRecord rec;
    private EntryAdder adder; 

    public CoreAccessor(StorageInterface storage) {
        rec = new TrainingRecord(storage);
        adder = new EntryAdder(rec);
    }

    public void setTrainingRecord(TrainingRecord newRec) {
        rec = newRec;
    }

    public void setEntryAdder(EntryAdder newAdder) {
        adder = newAdder;
    }

    public void addEntry(String entryName) {
        adder.addEntry(entryName);
    }

    public void setActiveDate(String isoformattedDate) {
        adder.setActiveDate(isoformattedDate);
    }

    public String getActiveDate() {
        return adder.getActiveDate();
    }

    public List<String> listEntries() {
        List<String> entries = new ArrayList<String>();
        for (ExerciseEntry entry : rec.listEntries()) {
            entries.add(String.format(
                    "%s %s", entry.getDate(), entry.getExercise()
                    ));
        }
        return entries;
    }
}
