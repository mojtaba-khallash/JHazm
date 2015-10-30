package jhazm.test.reader;

import jhazm.model.Document;
import jhazm.reader.HamshahriReader;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mojtaba on 30/10/2015.
 */
public class HamshahriReaderTest {

    @Test
    public void getDocumentsTest() {
        HamshahriReader hr = new HamshahriReader();
        String expected = "HAM2-750403-001";
        Iterator<Document> iter = hr.getDocuments().iterator();
        String actual = iter.next().getID();
        assertEquals("Failed to read hamshahri corpus.", expected, actual);
    }
}
