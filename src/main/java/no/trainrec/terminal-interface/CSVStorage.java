package no.trainrec.terminal_interface;

import no.trainrec.core.data.StorageInterface;
import no.trainrec.core.data.TrainingRecord;

public class CSVStorage implements StorageInterface {
    public TrainingRecord load() {
        return new TrainingRecord(this);
    }
    public void save(TrainingRecord rec) {}

}
