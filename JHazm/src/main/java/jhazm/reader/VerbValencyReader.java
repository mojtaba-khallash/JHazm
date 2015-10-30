package jhazm.reader;

import com.infomancers.collections.yield.Yielder;
import jhazm.model.Verb;

import java.io.*;
import java.nio.charset.Charset;

/**
 * interfaces [Verb Valency Corpus](http://dadegan.ir/catalog/pervallex)
 * Mohammad Sadegh Rasooli, Amirsaeid Moloodi, Manouchehr Kouhestani, & Behrouz Minaei Bidgoli. (2011). A Syntactic Valency Lexicon for Persian Verbs: The First Steps towards Persian Dependency Treebank. in 5th Language & Technology Conference(LTC): Human Language Technologies as a Challenge for Computer Science and Linguistics(pp. 227�231). Pozna?, Poland.
 *
 * Created by Mojtaba Khallash on 30/10/2015.
 */
public class VerbValencyReader {
    //
    // Fields
    //

    private String valencyFile;



    //
    // Constructors
    //

    public VerbValencyReader() {
        this("resources/corpora/valency.txt");
    }

    public VerbValencyReader(String valencyFile) {
        this.valencyFile = valencyFile;
    }




    // 
    // API
    //

    public Iterable<Verb> getVerbs() throws IOException { return new YieldVernValency(); }




    //
    // Helper
    //

    private String getValencyFile() {
        return this.valencyFile;
    }

    class YieldVernValency extends Yielder<Verb> {
        private BufferedReader br;

        public YieldVernValency() {
            try {
                FileInputStream fstream = new FileInputStream(getValencyFile());
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
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.contains("بن ماضی"))
                        continue;

                    line = line.trim().replace("-\t", "\t");
                    String[] parts = line.split("\t");
                    if (parts.length == 6) {
                        yieldReturn(new Verb(
                            parts[0],       // PastLightVerb
                            parts[1],       // PresentLightVerb
                            parts[2],       // Prefix
                            parts[3],       // NonVerbalElement
                            parts[4],       // Preposition
                            parts[5]));     // Valency
                        return;
                    }
                }
                br.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}