package no.trainrec.terminal_interface;

import no.trainrec.core.data.StorageInterface;
import no.trainrec.core.domain.ExerciseEntry;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

public class CSVStorage implements StorageInterface {
    private FileIO io;

    public CSVStorage(FileIO inputIO) {
        io = inputIO;
    }

    public List<ExerciseEntry> load() {
        List<ExerciseEntry> entries = new ArrayList<ExerciseEntry>();
        String row = "";
        try {
            io.openReader();
            while ((row = io.readLine()) != null) {
                String[] data = row.split(",");
                entries.add(new ExerciseEntry(data[0], data[1]));
            }
            io.closeReader();
        } catch (IOException ex) {}
        return entries;
    }
    public void save(List<ExerciseEntry> entries) {
        List<String> lines = new ArrayList<String>();
        for (ExerciseEntry entry : entries) {
            lines.add(String.format(
                        "%s,%s", entry.getDate(), entry.getExercise())
                    );
        }
        try {
            io.openWriter();
            io.write(String.join("\n", lines));
            io.closeWriter();
        } catch (IOException ex) {}
    }
}
