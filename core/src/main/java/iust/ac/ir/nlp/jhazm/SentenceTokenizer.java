package iust.ac.ir.nlp.jhazm;

import iust.ac.ir.nlp.jhazm.utility.RegexPattern;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mojtaba Khallash
 */
public class SentenceTokenizer {
    public static SentenceTokenizer instance;
    private final RegexPattern pattern;

    public SentenceTokenizer() {
        this.pattern = new RegexPattern("([!\\.\\?⸮؟]+)[ \\n]+", "$1\n\n");
    }

    public static SentenceTokenizer i() {
        if (instance != null) return instance;
        instance = new SentenceTokenizer();
        return instance;
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