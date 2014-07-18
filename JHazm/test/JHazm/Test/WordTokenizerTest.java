package JHazm.Test;

import JHazm.WordTokenizer;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Mojtaba Khallash
 */
public class WordTokenizerTest {
    
    @Test
    public void TokenizeTest() throws IOException {
        WordTokenizer wordTokenizer = new WordTokenizer(false);

        String input;
        String[] expected;
        List<String> actual;

        input = "این جمله (خیلی) پیچیده نیست!!!";
        expected = new String[] { "این", "جمله", "(", "خیلی", ")", "پیچیده", "نیست", "!!!"};
        actual = wordTokenizer.Tokenize(input);
        check(input, expected, actual);
    }

    @Test
    public void JoinVerbPartsTest() throws IOException {
        WordTokenizer wordTokenizer = new WordTokenizer(true);

        String input;
        String[] expected;
        List<String> actual;

        input = "خواهد رفت";
        expected = new String[] { "خواهد رفت" };
        actual = wordTokenizer.Tokenize(input);
        check(input, expected, actual);

        input = "رفته است";
        expected = new String[] { "رفته است" };
        actual = wordTokenizer.Tokenize(input);
        check(input, expected, actual);

        input = "گفته شده است";
        expected = new String[] { "گفته شده است" };
        actual = wordTokenizer.Tokenize(input);
        check(input, expected, actual);

        input = "گفته خواهد شد";
        expected = new String[] { "گفته خواهد شد" };
        actual = wordTokenizer.Tokenize(input);
        check(input, expected, actual);

        input = "خسته شدید";
        expected = new String[] { "خسته", "شدید" };
        actual = wordTokenizer.Tokenize(input);
        check(input, expected, actual);
    }
    
    private void check(String input, String[] expected, List<String> actual) {
        assertEquals("Failed to tokenize words of '" + input + "' sentence", expected.length, actual.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Failed to tokenize words of '" + input + "' sentence", expected[i], actual.get(i));
        }
    }
}