package no.trainrec.terminal_interface;

import no.trainrec.core.domain.ExerciseEntry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import org.mockito.Mockito;

import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;

public class CSVStorageTest {
    private BufferedReader reader;

    private CSVStorage storage;

    @BeforeEach
    public void setUp() {
        reader = Mockito.mock(BufferedReader.class);
        storage = new CSVStorage(reader);
    }

    @Test
    public void testLoad() throws IOException {
        // Return correctly formatted line on first and second call,
        // null casted to String on third
        Mockito.when(reader.readLine()).thenReturn(
                "2020-10-10,Squat", 
                "2020-10-11,Bench press",
                (String) null
                );

        List<ExerciseEntry> entries = storage.load();
        ExerciseEntry squat = entries.get(0);
        ExerciseEntry bench = entries.get(1);

        Assertions.assertEquals(2, entries.size());
        Assertions.assertEquals("2020-10-10", squat.getDate());
        Assertions.assertEquals("Squat", squat.getExercise());
        Assertions.assertEquals("2020-10-11", bench.getDate());
        Assertions.assertEquals("Bench press", bench.getExercise());
    }
}
