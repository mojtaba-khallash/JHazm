package iust.ac.ir.nlp.jhazm;

import com.infomancers.collections.yield.Yielder;
import edu.stanford.nlp.ling.TaggedWord;
import iust.ac.ir.nlp.jhazm.io.FileHandler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

/**
 * interfaces Bijankhan Corpus (http://ece.ut.ac.ir/dbrg/bijankhan/Corpus/BijanKhan_Corpus_Processed.zip) that
 * you must download and extract it.
 *
 * @author Mojtaba Khallash
 */
public class BijankhanReader {
    private final String[] punctuation = new String[] { "#", "*", ".", "؟", "!" };

    private String bijankhanFile;
    private boolean joinedVerbParts;
    private String posMap;
    private Normalizer normalizer;
    private WordTokenizer tokenizer;

    public BijankhanReader() throws IOException {
        this("resources/bijankhan.txt", true, "data/posMaps.dat");
    }

    public BijankhanReader(boolean joinedVerbParts) throws IOException {
        this("resources/bijankhan.txt", joinedVerbParts, "data/posMaps.dat");
    }

    public BijankhanReader(String posMap) throws IOException {
        this("resources/bijankhan.txt", true, posMap);
    }

    public BijankhanReader(boolean joinedVerbParts, String posMap)
            throws IOException {
        this("resources/bijankhan.txt", joinedVerbParts, posMap);
    }

    public BijankhanReader(String bijankhanFile, boolean joinedVerbParts, String posMap)
            throws IOException {
        this.bijankhanFile = bijankhanFile;
        this.joinedVerbParts = joinedVerbParts;
        this.posMap = posMap;
        this.normalizer = new Normalizer(true, false, true);
        this.tokenizer = new WordTokenizer();
    }

    public String getBijankhanFile() {
        return bijankhanFile;
    }

    public boolean isJoinedVerbParts() {
        return joinedVerbParts;
    }

    private HashMap GetPosMap() throws IOException
    {
        if (this.posMap != null)
        {
            HashMap mapper = new HashMap();
            for (String line : Files.readAllLines(FileHandler.getPath(this.posMap), Charset.forName("UTF8")))
            {
                String[] parts = line.split(",");
                mapper.put(parts[0], parts[1]);
            }

            return mapper;
        }
        else
            return null;
    }

    public Normalizer getNormalizer() {
        return normalizer;
    }

    public Iterable<List<TaggedWord>> GetSentences() {
        return new YieldSentence();
    }

    private List<TaggedWord> JoinVerbParts(List<TaggedWord> sentence) {
        Collections.reverse(sentence);
        List<TaggedWord> result = new ArrayList<>();
        TaggedWord beforeTaggedWord = new TaggedWord("", "");
        for (TaggedWord taggedWord : sentence) {
            if (this.tokenizer.getBeforeVerbs().contains(taggedWord.word()) ||
                    (this.tokenizer.getAfterVerbs().contains(beforeTaggedWord.word()) &&
                            this.tokenizer.getVerbs().contains(taggedWord.word()))) {
                beforeTaggedWord.setWord(taggedWord.word() + " " + beforeTaggedWord.word());
                if (result.isEmpty())
                    result.add(beforeTaggedWord);
            } else {
                result.add(taggedWord);
                beforeTaggedWord = taggedWord;
            }
        }

        Collections.reverse(result);
        return result;
    }

    class YieldSentence extends Yielder<List<TaggedWord>> {
        private BufferedReader br;

        public YieldSentence() {
            try {
                FileInputStream fstream = new FileInputStream(FileHandler.getPath(getBijankhanFile()).toFile());
                DataInputStream in = new DataInputStream(fstream);
                br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF8")));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void yieldNextCore() {
            try {
                HashMap mapper = GetPosMap();
                List<TaggedWord> sentence = new ArrayList<>();

                String line;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.trim().split("  +");
                    if (parts.length == 2) {
                        String word = parts[0];
                        String tag = parts[1];
                        if (!(word.equals("#") || word.equals("*"))) {
                            word = getNormalizer().Run(word);
                            if (word.isEmpty())
                                word = "_";
                            sentence.add(new TaggedWord(word, tag));
                        }
                        if (tag.equals("DELM") && Arrays.asList(punctuation).contains(word)) {
                            if (!sentence.isEmpty()) {
                                if (isJoinedVerbParts())
                                    sentence = JoinVerbParts(sentence);

                                if (mapper != null) {
                                    for (TaggedWord tword : sentence) {
                                        tword.setTag(mapper.get(tword.tag()).toString());
                                    }
                                }

                                yieldReturn(sentence);
                                return;
                            }
                        }
                    }
                }
                br.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}