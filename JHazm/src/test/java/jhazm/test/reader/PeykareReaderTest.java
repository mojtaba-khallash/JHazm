package jhazm.test.reader;

import edu.stanford.nlp.ling.TaggedWord;
import jhazm.reader.PeykareReader;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mojtaba on 30/10/2015.
 */
public class PeykareReaderTest {

    @Test
    public void coarsePOSTest() {
        List<String> input = Arrays.asList(new String[]{ "N", "COM", "SING" });
        String expected = "N";
        String actual = PeykareReader.coarsePOS(input);
        assertEquals("Failed to find coarse pos.", expected, actual);
    }

    @Test
    public void joinVerbPartsTest() {
        List<TaggedWord> input = Arrays.asList(new TaggedWord[] {
                new TaggedWord("اولین", "AJ"),
                new TaggedWord("سیاره", "Ne"),
                new TaggedWord("", "AJ"),
                new TaggedWord("از", "P"),
                new TaggedWord("منظومه", "Ne"),
                new TaggedWord("شمسی", "AJ"),
                new TaggedWord("دیده", "AJ"),
                new TaggedWord("شد", "V"),
                new TaggedWord(".", "PUNC")
        });

        List<TaggedWord> expected = Arrays.asList(new TaggedWord[] {
                new TaggedWord("اولین", "AJ"),
                new TaggedWord("سیاره", "Ne"),
                new TaggedWord("خارج", "AJ"),
                new TaggedWord("از", "P"),
                new TaggedWord("منظومه", "Ne"),
                new TaggedWord("شمسی", "AJ"),
                new TaggedWord("دیده شد", "V"),
                new TaggedWord(".", "PUNC")
        });
        List<TaggedWord> actual = PeykareReader.joinVerbParts(input);

        assertEquals("Failed to join verb parts of sentence", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++)
        {
            TaggedWord actualTaggedWord = actual.get(i);
            TaggedWord expectedTaggedWord = expected.get(i);
            if (!actualTaggedWord.tag().equals(expectedTaggedWord.tag()))
                assertEquals("Failed to join verb parts of sentence", expectedTaggedWord, actualTaggedWord);
        }
    }
}
