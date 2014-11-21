package JHazm;

import JHazm.Utility.Document;
import JHazm.Utility.RegexPattern;
import com.infomancers.collections.yield.Yielder;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * interfaces Hamshahri Corpus (http://ece.ut.ac.ir/dbrg/hamshahri/files/HAM2/Corpus.zip) 
 * that you must download and extract it.
 * 
 * @author Mojtaba Khallash
 */
public class HamshahriReader {
    private RegexPattern paragraphPattern;
    public RegexPattern getParagraphPattern() {
        return paragraphPattern;
    }
    
    private String rootFolder;
    public String getRootFolder() {
        return rootFolder;
    }
        
    private final String[] invalidFiles = new String[] {
        "hamshahri.dtd", "HAM2-960622.xml", "HAM2-960630.xml", "HAM2-960701.xml", "HAM2-960709.xml", 
        "HAM2-960710.xml", "HAM2-960711.xml", "HAM2-960817.xml", "HAM2-960818.xml", "HAM2-960819.xml", 
        "HAM2-960820.xml", "HAM2-961019.xml", "HAM2-961112.xml", "HAM2-961113.xml", "HAM2-961114.xml", 
        "HAM2-970414.xml", "HAM2-970415.xml", "HAM2-970612.xml", "HAM2-970614.xml", "HAM2-970710.xml", 
        "HAM2-970712.xml", "HAM2-970713.xml", "HAM2-970717.xml", "HAM2-970719.xml", "HAM2-980317.xml", 
        "HAM2-040820.xml", "HAM2-040824.xml", "HAM2-040825.xml", "HAM2-040901.xml", "HAM2-040917.xml", 
        "HAM2-040918.xml", "HAM2-040920.xml", "HAM2-041025.xml", "HAM2-041026.xml", "HAM2-041027.xml", 
        "HAM2-041230.xml", "HAM2-041231.xml", "HAM2-050101.xml", "HAM2-050102.xml", "HAM2-050223.xml", 
        "HAM2-050224.xml", "HAM2-050406.xml", "HAM2-050407.xml", "HAM2-050416.xml"
    };
    public List<String> getInvalidFiles() {
        return Arrays.asList(invalidFiles);
    }

    public HamshahriReader() {
        this("resources/hamshahri");
    }

    public HamshahriReader(String root) {
        this.rootFolder = root;
        this.paragraphPattern = new RegexPattern("(\n.{0,50})(?=\n)", "$1\n");
    }

    public Iterable<Document> GetDocuments() {
        return new YieldDocument();
    }
    
    class YieldDocument extends Yielder<Document> {

        private final List<String> allFiles;
        private int index;
        private int docLength;
        private int docIndex;
        private NodeList DOCs;
        
        public YieldDocument() {
            allFiles = fileList(getRootFolder());
            index = -1;
            docLength = 0;
            docIndex =  -1;
            DOCs = null;
        }
        
        private List<String> fileList(String path){
            return fileList(new File(path));
        }
        
        private List<String> fileList(File dir){
            List<String> fs = new ArrayList<>();
            
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    return fileList(file);
                } else {
                    fs.add(file.getAbsolutePath());
                }
            }
            
            return fs;
        }
        
        @Override
        protected void yieldNextCore() {
            boolean isOpen = docLength > 0 && docIndex + 1 < docLength;
            
            File file = null;
            if (!isOpen) {
                do {
                    index++;
                    if (index >= allFiles.size()) {
                        yieldBreak();
                        return;
                    }
                    file = new File(allFiles.get(index));
                } while (getInvalidFiles().contains(file.getName()));
            }

            try {
                if (!isOpen) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    org.w3c.dom.Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    DOCs = doc.getElementsByTagName("DOC");
                    docIndex = -1;
                    docLength = DOCs.getLength();
                }

                docIndex++;
                Element DOC = (Element)DOCs.item(docIndex);

                Element DOCID = (Element)DOC.getElementsByTagName("DOCID").item(0);
                Element DOCNO = (Element)DOC.getElementsByTagName("DOCNO").item(0);
                Element ORIGINALFILE = (Element)DOC.getElementsByTagName("ORIGINALFILE").item(0);
                Element ISSUE = (Element)DOC.getElementsByTagName("ISSUE").item(0);
                Element TITLE = (Element)DOC.getElementsByTagName("TITLE").item(0);
                
                NodeList DATEs = DOC.getElementsByTagName("DATE");
                String WesternDate = "";
                String PersianDate = "";
                for (int i = 0; i < DATEs.getLength(); i++) {
                    Element DATE = (Element)DATEs.item(i);
                    switch (DATE.getAttribute("calender")) {
                        case "Western":
                            WesternDate = DATE.getTextContent();
                            break;
                        case "Persian":
                            PersianDate = DATE.getTextContent();
                            break;
                    }
                }
                
                NodeList CATs = DOC.getElementsByTagName("CAT");
                String EnglishCategory = "";
                String PersianCategory = "";
                for (int i = 0; i < CATs.getLength(); i++) {
                    Element CAT = (Element)CATs.item(i);
                    switch (CAT.getAttribute("xml:lang")) {
                        case "en":
                            EnglishCategory = CAT.getTextContent();
                            break;
                        case "fa":
                            PersianCategory = CAT.getTextContent();
                            break;
                    }
                }

                // refine text
                Element TEXT = (Element)DOC.getElementsByTagName("TEXT").item(0);
                String body = TEXT.getTextContent();
                body = getParagraphPattern().Apply(body).replace("\no ", "\n");

                Document document = new Document();
                document.setID(DOCID.getTextContent());
                document.setNumber(DOCNO.getTextContent());
                document.setOriginalFile(ORIGINALFILE.getTextContent());
                document.setIssue(ISSUE.getTextContent());
                document.setWesternDate(WesternDate);
                document.setPersianDate(PersianDate);
                document.setEnglishCategory(EnglishCategory);
                document.setPersianCategory(PersianCategory);
                document.setTitle(TITLE.getTextContent());
                document.setBody(body);

                yieldReturn(document);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
