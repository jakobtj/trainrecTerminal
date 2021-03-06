package no.trainrec.terminal_interface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.mockito.Mockito;

import java.util.List;
import java.util.ArrayList;

import no.trainrec.core.data.TrainingRecord;
import no.trainrec.core.use_case.EntryAdder;
import no.trainrec.core.domain.ExerciseEntry;
import no.trainrec.core.data.StorageInterface;

public class CoreAccessorTest {
    private TrainingRecord rec;
    private EntryAdder adder;
    private CoreAccessor accessor; 

    @BeforeEach
    public void setUp() {
        accessor = new CoreAccessor(Mockito.mock(StorageInterface.class));
        rec = Mockito.mock(TrainingRecord.class);
        adder = Mockito.mock(EntryAdder.class);
        accessor.setTrainingRecord(rec);
        accessor.setEntryAdder(adder);
    }

    @Test
    public void testAddCallsEntryAdder() {
        accessor.addEntry("Squat");
        Mockito.verify(adder).addEntry("Squat");
    }

    @Test
    public void testListEntries() {
        ExerciseEntry entry = Mockito.mock(ExerciseEntry.class);
        Mockito.when(entry.getDate()).thenReturn("2020-10-01");
        Mockito.when(entry.getExercise()).thenReturn("Squat");
        List<ExerciseEntry> entryList = new ArrayList<ExerciseEntry>();
        entryList.add(entry);
        Mockito.when(rec.listEntries()).thenReturn(entryList);

        List<String> entries = accessor.listEntries();

        Assertions.assertEquals(1, entries.size());
        Assertions.assertEquals("2020-10-01 Squat", entries.get(0));
    }

    @Test
    public void testSetActiveDateCallsEntryAdder() {
        accessor.setActiveDate("2020-10-01");

        Mockito.verify(adder).setActiveDate("2020-10-01");
    }
}
