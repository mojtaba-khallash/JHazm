package JHazm.Test;

import JHazm.SentenceTokenizer;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mojtaba Khallash
 */
public class SentenceTokenizerTests {

    public SentenceTokenizerTests() {
    }

    @Test
    public void tokenizeTest() {
        SentenceTokenizer senTokenizer = new SentenceTokenizer();
        
        String input = "جدا کردن ساده است. تقریبا البته!";
        System.out.println("input: " + input);
        
        String[] expected = new String[] { "جدا کردن ساده است.", "تقریبا البته!" };
        
        System.out.println("actual: ");
        List<String> actual = senTokenizer.Tokenize(input);
        
        assertEquals("Failed to tokenize sentences of '" + input + "' passage", expected.length, actual.size());
        for (int i = 0; i < actual.size(); i++) {
            String sentence = actual.get(i);
            System.out.println("\t" + sentence);
            assertEquals("Failed to tokenize sentences of '" + input + "' passage", expected[i], actual.get(i));
        }
    }
}