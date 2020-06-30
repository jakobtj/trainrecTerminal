package no.trainrec.terminal_interface;

import no.trainrec.core.StorageInterface;
import no.trainrec.core.TrainingRecord;

public class CSVStorage implements StorageInterface {
    public TrainingRecord load() {
        return new TrainingRecord(this);
    }
    public void save(TrainingRecord rec) {}

}
