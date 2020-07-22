package no.trainrec.terminal_interface;

import no.trainrec.core.domain.ExerciseEntry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import org.mockito.Mockito;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class CSVStorageTest {
    private FileIO io;

    private CSVStorage storage;

    @BeforeEach
    public void setUp() {
        io = Mockito.mock(FileIO.class);
        storage = new CSVStorage(io);
    }

    @Test
    public void testLoad() throws IOException {
        // Return correctly formatted line on first and second call,
        // null casted to String on third
        Mockito.when(io.readLine()).thenReturn(
                "2020-10-10,Squat", 
                "2020-10-11,Bench press",
                (String) null
                );

        List<ExerciseEntry> entries = storage.load();
        ExerciseEntry squat = entries.get(0);
        ExerciseEntry bench = entries.get(1);

        Mockito.verify(io).openReader();
        Mockito.verify(io).closeReader();
        Assertions.assertEquals(2, entries.size());
        Assertions.assertEquals("2020-10-10", squat.getDate());
        Assertions.assertEquals("Squat", squat.getExercise());
        Assertions.assertEquals("2020-10-11", bench.getDate());
        Assertions.assertEquals("Bench press", bench.getExercise());
    }

    @Test
    public void testSave() throws IOException {
        List<ExerciseEntry> entries = new ArrayList<ExerciseEntry>();
        entries.add(new ExerciseEntry("2020-10-10", "Squat"));
        entries.add(new ExerciseEntry("2020-10-11", "Bench press"));
        
        storage.save(entries);

        Mockito.verify(io).openWriter();
        Mockito.verify(io).write(
                "2020-10-10,Squat\n2020-10-11,Bench press"
                );
        Mockito.verify(io).closeWriter();
    }
}
