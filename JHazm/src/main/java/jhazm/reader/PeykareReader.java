package jhazm.reader;

import edu.stanford.nlp.ling.TaggedWord;
import jhazm.tokenizer.WordTokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mojtaba on 30/10/2015.
 */
public class PeykareReader {
    private static List<String> cpos;
    private static WordTokenizer tokenizer = null;


    static {
        try {
            cpos = Arrays.asList(new String[] {
                        "N",        // Noun
                        "V",        // Verb
                        "AJ",       // Adjective
                        "ADV",      // Adverb
                        "PRO",      // Pronoun
                        "DET",      // Determiner
                        "P",        // Preposition
                        "POSTP",    // Postposition
                        "NUM",      // Number
                        "CONJ",     // Conjunction
                        "PUNC",     // Punctuation
                        "RES",      // Residual
                        "CL",       // Classifier
                        "INT"       // Interjection
                });

            tokenizer = new WordTokenizer();
        } catch (IOException e) {
        }
    }

    /**
     * Coarse POS tags of Peykare corpus:
     */
    public static String coarsePOS(List<String> tags) {
        try {
            String result = "N";
            for (String tag : tags) {
                if (cpos.contains(tag)) {
                    result = tag;
                    break;
                }
            }

            if (tags.contains("EZ"))
                result += "e";
            return result;
        }
        catch(Exception ex) {
            return "N";
        }
    }

    /**
     * Join verb parts like Dadedgan corpus.
     * Input:
     *     دیده/ADJ_INO
    *     شد/V_PA
     * Iutput:
     *     دیده شد/V_PA
     */
    public static List<TaggedWord> joinVerbParts(List<TaggedWord> sentence) {
        Collections.reverse(sentence);
        List<TaggedWord> result = new ArrayList<>();
        TaggedWord beforeTaggedWord = new TaggedWord("", "");
        for (TaggedWord taggedWord : sentence) {
            if (PeykareReader.tokenizer.getBeforeVerbs().contains(taggedWord.word()) ||
                    (PeykareReader.tokenizer.getAfterVerbs().contains(beforeTaggedWord.word()) &&
                            PeykareReader.tokenizer.getVerbs().contains(taggedWord.word()))) {
                beforeTaggedWord.setWord(taggedWord.word() + " " + beforeTaggedWord.word());
                if (result.isEmpty())
                    result.add(beforeTaggedWord);
            }
            else {
                result.add(taggedWord);
                beforeTaggedWord = taggedWord;
            }
        }

        Collections.reverse(result);
        return result;
    }
}
