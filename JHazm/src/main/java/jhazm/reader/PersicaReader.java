package jhazm.reader;

import com.infomancers.collections.yield.Yielder;
import jhazm.model.Doc;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * interfaces [Persica Corpus](https://sourceforge.net/projects/persica/)
 *
 * Created by Mojtaba on 30/10/2015.
 */
public class PersicaReader {

    //
    // Fields
    //

    private String persicaFile;



    //
    // Constructors
    //

    public PersicaReader() {
        this("resources/corpora/persica.csv");
    }

    public PersicaReader(String persicaFile) {
        this.persicaFile = persicaFile;
    }




    //
    // API
    //

    public Iterable<Doc> getDocs() { return new YieldDoc(); }

    public Iterable<String> getTexts() { return  new YieldtText(); }





    //
    // Helper
    //

    private String getPersicaFile() {
        return persicaFile;
    }

    class YieldDoc extends Yielder<Doc> {
        private BufferedReader br;

        public YieldDoc() {
            try {
                FileInputStream fstream = new FileInputStream(getPersicaFile());
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
                List<String> lines = new ArrayList<>();
                String line;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.length() > 0) {
                        if (line.endsWith(",")) {
                            lines.add(StringUtils.stripEnd(line, ","));
                        }
                        else {
                            lines.add(line);
                            yieldReturn(new Doc(
                                    Integer.parseInt(lines.get(0)), // ID
                                    lines.get(1),                   // Title
                                    lines.get(2),                   // Text
                                    lines.get(3),                   // Date
                                    lines.get(4),                   // Time
                                    lines.get(5),                   // Category
                                    lines.get(6)));                 // Category2

                            lines = new ArrayList<>();
                            return;
                        }
                    }
                }
                br.close();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    class YieldtText extends Yielder<String> {
        private Iterator<Doc> iter;

        public YieldtText() {
            try {
                iter = getDocs().iterator();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void yieldNextCore() {
            try {
                while (iter.hasNext()) {
                    Doc doc = iter.next();
                    yieldReturn(doc.Text);
                    return;
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
