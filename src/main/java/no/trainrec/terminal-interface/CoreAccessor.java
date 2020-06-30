package no.trainrec.terminal_interface;

import no.trainrec.core.TrainingRecord;
import no.trainrec.core.EntryAdder;

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
}
