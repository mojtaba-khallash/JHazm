package JHazm;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class POSTagger {
    private MaxentTagger tagger;

    public POSTagger() {
        this("resources/persian.tagger");
    }

    public POSTagger(String pathToModel) {
        this.tagger = new MaxentTagger(pathToModel);
    }

    public List<List<TaggedWord>> BatchTags(List<List<String>> sentences) {
        List<List<TaggedWord>> result = new ArrayList<>();

        for (List<String> sentence : sentences) {
            result.add(BatchTag(sentence));
        }

        return result;
    }

    public List<TaggedWord> BatchTag(List<String> sentence) {
        String[] sen = new String[sentence.size()];
        for (int i = 0; i < sentence.size(); i++)
           sen[i] = sentence.get(i).replace(" ", "_");
        List newSent = Sentence.toWordList(sen);
        ArrayList taggedSentence = this.tagger.tagSentence(newSent);

        List<TaggedWord> taggedSen = new ArrayList<>();
        for (int i = 0; i < taggedSentence.size(); i++) {
            TaggedWord tw = (TaggedWord)taggedSentence.get(i);
            tw.setWord(sentence.get(i));
            taggedSen.add(tw);
        }
        return taggedSen;
    }
}