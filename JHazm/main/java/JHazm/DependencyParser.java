package JHazm;

import com.infomancers.collections.yield.Yielder;
import edu.stanford.nlp.ling.TaggedWord;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.maltparser.concurrent.ConcurrentMaltParserModel;
import org.maltparser.concurrent.ConcurrentMaltParserService;
import org.maltparser.concurrent.graph.ConcurrentDependencyGraph;
import org.maltparser.core.exception.MaltChainedException;

/**
 *
 * @author Mojtaba Khallash
 */
public class DependencyParser {
    private SentenceTokenizer sentenceTokenizer;
    public SentenceTokenizer getSentenceTokenizer() {
        if (sentenceTokenizer == null)
            sentenceTokenizer = new SentenceTokenizer();
        return sentenceTokenizer;
    }
    public void setSentenceTokenizer(SentenceTokenizer value) {
        this.sentenceTokenizer = value;
    }

    private WordTokenizer wordTokenizer;
    public WordTokenizer getWordTokenizer() throws IOException {
        if (wordTokenizer == null)
            wordTokenizer = new WordTokenizer();
        return wordTokenizer;
    }
    public void setWordTokenizer(WordTokenizer value) {
        this.wordTokenizer = value;
    }

    private Normalizer normalizer;
    public Normalizer getNormalizer() {
        return normalizer;
    }
    public void setNormalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
    }

    private Lemmatizer lemmatizer;
    public Lemmatizer getLemmatizer() {
        return lemmatizer;
    }
    public void setLemmatizer(Lemmatizer lemmatizer) {
        this.lemmatizer = lemmatizer;
    }
    
    public POSTagger tagger;
    public POSTagger getTagger() {
        if (tagger == null)
            tagger = new POSTagger();
        return tagger;
    }
    public void setTagger(POSTagger value) {
        this.tagger = value;
    }
    
    private String modelFile;
    private ConcurrentMaltParserModel model;
    private ConcurrentMaltParserModel getModel() 
            throws MalformedURLException, 
                   MaltChainedException {
        if (model == null) {
            URL maltModelURL = new File(this.modelFile).toURI().toURL();
            this.model = ConcurrentMaltParserService.initializeParserModel(maltModelURL);
        }
        return model;
    }

    public DependencyParser() { 
        this(null, null, "resources/langModel.mco");
    }

    public DependencyParser(POSTagger tagger, Lemmatizer lemmatizer, String modelFile) {
        this.tagger = tagger;
        this.lemmatizer = lemmatizer;
        this.modelFile = modelFile;
    }

    // Gets list of raw text
    public Iterable<ConcurrentDependencyGraph> RawParse(String text) 
            throws IOException {
        if (this.normalizer != null)
            text = this.normalizer.Run(text);
        return RawParses(getSentenceTokenizer().Tokenize(text));
    }

    // Gets list of raw sentences
    public Iterable<ConcurrentDependencyGraph> RawParses(List<String> sentences) 
            throws IOException {
        return new YieldParsedSentence(sentences);
    }
    
    class YieldParsedSentence extends Yielder<ConcurrentDependencyGraph> {
        private final List<String> sentences;
        private int index;
        
        public YieldParsedSentence(List<String> sentences) {
            this.sentences = sentences;
            index = -1;
        }
        
        @Override
        protected void yieldNextCore() {
            try {
                index++;
                if (index < sentences.size()) {
                    String sentence = sentences.get(index);
                    List<String> words = getWordTokenizer().Tokenize(sentence);
                    yieldReturn(RawParse(getTagger().BatchTag(words)));
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public ConcurrentDependencyGraph RawParse(List<TaggedWord> sentence) 
            throws MalformedURLException, 
                   MaltChainedException {
        String[] conll = new String[sentence.size()];
        for (int i = 0; i < sentence.size(); i++) {
            TaggedWord taggedWord = sentence.get(i);
            String word = taggedWord.word();
            String Lemma = "_";
            if (this.lemmatizer != null)
                Lemma = this.lemmatizer.Lemmatize(word);
            String pos = taggedWord.tag();

            conll[i] = String.format("%s\t%s\t%s\t%s\t%s\t%s",
                    i + 1, word, Lemma, pos, pos, "_");
        }
        return Parse(conll);
    }

    public ConcurrentDependencyGraph Parse(String[] conllSentence) 
            throws MalformedURLException, 
                   MaltChainedException {
        return this.getModel().parse(conllSentence);
    }
}