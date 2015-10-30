package jhazm.test.reader;

import edu.stanford.nlp.ling.TaggedWord;
import jhazm.reader.BijankhanReader;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Mojtaba Khallash
 */
public class BijankhanReaderTest {

    @Test
    public void posMapTest() throws IOException {
        BijankhanReader reader = new BijankhanReader(false);

        List<TaggedWord> expected = new ArrayList<>();
        expected.add(new TaggedWord("اولین", "ADJ"));
        expected.add(new TaggedWord("سیاره", "N"));
        expected.add(new TaggedWord("خارج", "ADJ"));
        expected.add(new TaggedWord("از", "PREP"));
        expected.add(new TaggedWord("منظومه", "N"));
        expected.add(new TaggedWord("شمسی", "ADJ"));
        expected.add(new TaggedWord("دیده", "ADJ"));
        expected.add(new TaggedWord("شد", "V"));
        expected.add(new TaggedWord(".", "PUNC"));
        Iterator<List<TaggedWord>> iter = reader.getSentences().iterator();
        List<TaggedWord> actual = iter.next();

        assertEquals("Failed to map pos of sentence", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            TaggedWord actualTaggedWord = actual.get(i);
            TaggedWord expectedTaggedWord = expected.get(i);
            if (!actualTaggedWord.tag().equals(expectedTaggedWord.tag()))
                assertEquals("Failed to map pos of sentence", expectedTaggedWord, actualTaggedWord);
        }
    }

    @Test
    public void joinVerbPartsTest() throws IOException {
        BijankhanReader reader = new BijankhanReader(null);

        List<TaggedWord> expected = new ArrayList<>();
        expected.add(new TaggedWord("اولین", "ADJ_SUP"));
        expected.add(new TaggedWord("سیاره", "N_SING"));
        expected.add(new TaggedWord("خارج", "ADJ_SIM"));
        expected.add(new TaggedWord("از", "P"));
        expected.add(new TaggedWord("منظومه", "N_SING"));
        expected.add(new TaggedWord("شمسی", "ADJ_SIM"));
        expected.add(new TaggedWord("دیده شد", "V_PA"));
        expected.add(new TaggedWord(".", "DELM"));
        Iterator<List<TaggedWord>> iter = reader.getSentences().iterator();
        List<TaggedWord> actual = iter.next();

        assertEquals("Failed to join verb parts of sentence", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            TaggedWord actualTaggedWord = actual.get(i);
            TaggedWord expectedTaggedWord = expected.get(i);
            if (!actualTaggedWord.tag().equals(expectedTaggedWord.tag()))
                assertEquals("Failed to join verb parts of sentence", expectedTaggedWord, actualTaggedWord);
        }
    }

    @Test
    public void posMapJoinVerbPartsTest() throws IOException {
        BijankhanReader reader = new BijankhanReader();

        List<TaggedWord> expected = new ArrayList<>();
        expected.add(new TaggedWord("اولین", "ADJ"));
        expected.add(new TaggedWord("سیاره", "N"));
        expected.add(new TaggedWord("خارج", "ADJ"));
        expected.add(new TaggedWord("از", "PREP"));
        expected.add(new TaggedWord("منظومه", "N"));
        expected.add(new TaggedWord("شمسی", "ADJ"));
        expected.add(new TaggedWord("دیده شد", "V"));
        expected.add(new TaggedWord(".", "PUNC"));
        Iterator<List<TaggedWord>> iter = reader.getSentences().iterator();
        List<TaggedWord> actual = iter.next();

        assertEquals("Failed to map pos and join verb parts of sentence", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            TaggedWord actualTaggedWord = actual.get(i);
            TaggedWord expectedTaggedWord = expected.get(i);
            if (!actualTaggedWord.tag().equals(expectedTaggedWord.tag()))
                assertEquals("Failed to map pos and join verb parts of sentence", expectedTaggedWord, actualTaggedWord);
        }
    }
}