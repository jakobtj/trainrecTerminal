package no.trainrec.terminal_interface;

import no.trainrec.core.domain.ExerciseEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.mockito.Mockito;

import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;

public class CSVStorageTest {
    private BufferedReader reader;

    private CSVStorage storage;

    @Before
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

        Assert.assertEquals(2, entries.size());
        Assert.assertEquals("2020-10-10", squat.getDate());
        Assert.assertEquals("Squat", squat.getExercise());
        Assert.assertEquals("2020-10-11", bench.getDate());
        Assert.assertEquals("Bench press", bench.getExercise());
    }
}
