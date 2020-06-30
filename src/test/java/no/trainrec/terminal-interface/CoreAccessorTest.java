package no.trainrec.terminal_interface;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import no.trainrec.core.TrainingRecord;
import no.trainrec.core.EntryAdder;

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
}
