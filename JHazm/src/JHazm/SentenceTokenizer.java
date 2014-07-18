package JHazm;

import JHazm.Utility.RegexPattern;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class SentenceTokenizer {
    private final RegexPattern pattern;

    public SentenceTokenizer() {
        this.pattern = new RegexPattern("([!\\.\\?⸮؟]+)[ \\n]+", "$1\n\n");
    }

    public List<String> Tokenize(String text) {
        text = this.pattern.Apply(text);
        List<String> sentences = Arrays.asList(text.split("\n\n"));
        for (String sentence : sentences) {
            sentence = sentence.replace("\n", " ").trim();
        }
        return sentences;
    }
}