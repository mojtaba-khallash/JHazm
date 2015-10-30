package jhazm;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class POSTagger {
    public static POSTagger instance;
    private MaxentTagger tagger;

    public POSTagger() throws IOException {
        this("resources/models/persian.tagger");
    }

    public POSTagger(String pathToModel) throws IOException {
        this.tagger = new MaxentTagger(pathToModel);
    }

    public static POSTagger i() throws IOException {
        if (instance != null) return instance;
        instance = new POSTagger();
        return instance;
    }

    public List<List<TaggedWord>> batchTags(List<List<String>> sentences) {
        List<List<TaggedWord>> result = new ArrayList<>();

        for (List<String> sentence : sentences) {
            result.add(batchTag(sentence));
        }

        return result;
    }

    public List<TaggedWord> batchTag(List<String> sentence) {
        String[] sen = new String[sentence.size()];
        for (int i = 0; i < sentence.size(); i++)
           sen[i] = sentence.get(i).replace(" ", "_");
        List newSent = Sentence.toWordList(sen);
        List taggedSentence = this.tagger.tagSentence(newSent);

        List<TaggedWord> taggedSen = new ArrayList<>();
        for (int i = 0; i < taggedSentence.size(); i++) {
            TaggedWord tw = (TaggedWord)taggedSentence.get(i);
            tw.setWord(sentence.get(i));
            taggedSen.add(tw);
        }
        return taggedSen;
    }
}