package jhazm.test.reader;

import jhazm.model.Verb;
import jhazm.reader.VerbValencyReader;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mojtaba on 30/10/2015.
 */
public class VerbValencyReaderTest {

    @Test
    public void getVerbsTest() throws IOException {
        VerbValencyReader vv = new VerbValencyReader();
        String expected = "بر";
        Iterator<Verb> iter = vv.getVerbs().iterator();
        iter.next();
        iter.next();
        iter.next();
        String actual = iter.next().Prefix;
        assertEquals("Failed to read verb valency corpus.", expected, actual);
    }
}
