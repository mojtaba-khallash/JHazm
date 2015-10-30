package jhazm.test;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.util.StringUtils;
import jhazm.POSTagger;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Mojtaba Khallash
 */
public class POSTaggerTest {
    
    @Test
    public void batchTagTest() throws IOException {
        POSTagger tagger = new POSTagger();

        String[] input = new String[] { "من", "به", "مدرسه", "رفته بودم", "."};
        List<TaggedWord> expected = new ArrayList<>();
        expected.add(new TaggedWord("من","PR"));
        expected.add(new TaggedWord("به","PREP"));
        expected.add(new TaggedWord("مدرسه","N"));
        expected.add(new TaggedWord("رفته بودم","V"));
        expected.add(new TaggedWord(".","PUNC"));
        List<TaggedWord> actual = tagger.batchTag(Arrays.asList(input));

        assertEquals("Failed to tagged words of '" + StringUtils.join(input, " ") + "' sentence", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            TaggedWord actualTaggedWord = actual.get(i);
            TaggedWord expectedTaggedWord = expected.get(i);
            if (!actualTaggedWord.tag().equals(expectedTaggedWord.tag()))
                assertEquals("Failed to tagged words of '" + StringUtils.join(input, " ") + "' sentence", expectedTaggedWord, actualTaggedWord);
        }
    }
}