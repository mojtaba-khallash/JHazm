package jhazm.test.reader;

import jhazm.model.Doc;
import jhazm.reader.PersicaReader;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mojtaba on 30/10/2015.
 */
public class PersicaReaderTest {

    @Test
    public void getDocsTest() {
        PersicaReader pr = new PersicaReader();
        int expected = 843656;
        Iterator<Doc> iter = pr.getDocs().iterator();
        int actual = iter.next().ID;
        assertEquals("Failed to read persica corpus.", expected, actual);
    }
}
