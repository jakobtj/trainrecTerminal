package no.trainrec.terminal_interface;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.mockito.Mockito;

import java.util.List;
import java.util.ArrayList;

import no.trainrec.core.TrainingRecord;
import no.trainrec.core.EntryAdder;
import no.trainrec.core.ExerciseEntry;

public class CoreAccessorTest {
    private TrainingRecord rec;
    private EntryAdder adder;
    private CoreAccessor accessor; 

    @Before
    public void setUp() {
        rec = Mockito.mock(TrainingRecord.class);
        adder = Mockito.mock(EntryAdder.class);
        accessor = new CoreAccessor(rec, adder);
    }

    @Test
    public void testAddCallsEntryAdder() {
        accessor.addEntry("Squat");
        Mockito.verify(adder).addEntry("Squat");
    }

    @Test
    public void testList() {
        ExerciseEntry entry = Mockito.mock(ExerciseEntry.class);
        Mockito.when(entry.getDate()).thenReturn("2020-10-01");
        Mockito.when(entry.getExercise()).thenReturn("Squat");
        List<ExerciseEntry> entryList = new ArrayList<ExerciseEntry>();
        entryList.add(entry);
        Mockito.when(rec.listEntries()).thenReturn(entryList);

        String entries = accessor.listEntries();

        Assert.assertEquals("\n2020-10-01 Squat", entries);
    }

    @Test
    public void testSetDate() {
        accessor.setDate("2020-10-01");

        Mockito.verify(adder).setDate("2020-10-01");
    }
}
