package no.trainrec.terminal_interface;

import no.trainrec.core.data.StorageInterface;
import no.trainrec.core.domain.ExerciseEntry;

import java.util.List;
import java.util.ArrayList;

public class CSVStorage implements StorageInterface {
    public List<ExerciseEntry> load() {
        return new ArrayList<ExerciseEntry>();
    }
    public void save(List<ExerciseEntry> entries) {}

}
