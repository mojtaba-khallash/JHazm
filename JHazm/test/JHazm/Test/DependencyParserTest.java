package JHazm.Test;

import JHazm.DependencyParser;
import JHazm.Lemmatizer;
import JHazm.Normalizer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.maltparser.concurrent.graph.ConcurrentDependencyGraph;
import org.maltparser.core.exception.MaltChainedException;

/**
 *
 * @author Mojtaba Khallash
 */
public class DependencyParserTest {
    
    @Test
    public void ParseTest() 
            throws MalformedURLException, 
                   MaltChainedException,
                   IOException {
        DependencyParser parser = new DependencyParser();
        String[] input = new String[] { 
            "1	از	_	PREP	PREP	_	12	VPP	_\t_",
            "2	نصایح	_	N	IANM	_",
            "3	باارزش	_	ADJ	AJP	_",
            "4	مولای	_	N	ANM	_",
            "5	متقیان	_	N	ANM	_",
            "6	حضرت	_	N	ANM	_",
            "7	علی	_	N	ANM	_",
            "8	(	_	PUNC	PUNC	_",
            "9	علیه‌السلام	_	N	IANM	_",
            "10	)	_	PUNC	PUNC	_",
            "11	پند	_	N	IANM	_",
            "12	بگیرید	_	V	ACT	_",
            "13	.	_	PUNC	PUNC	_",
        };
        String expected = 
            "1	از	_	PREP	PREP	_	12	VPP	_	_\n" +
            "2	نصایح	_	N	IANM	_	1	POSDEP	_	_\n" +
            "3	باارزش	_	ADJ	AJP	_	2	MOZ	_	_\n" +
            "4	مولای	_	N	ANM	_	3	MOZ	_	_\n" +
            "5	متقیان	_	N	ANM	_	4	MOZ	_	_\n" +
            "6	حضرت	_	N	ANM	_	5	MOZ	_	_\n" +
            "7	علی	_	N	ANM	_	6	MOZ	_	_\n" +
            "8	(	_	PUNC	PUNC	_	7	MOZ	_	_\n" +
            "9	علیه‌السلام	_	N	IANM	_	8	POSDEP	_	_\n" +
            "10	)	_	PUNC	PUNC	_	9	PUNC	_	_\n" +
            "11	پند	_	N	IANM	_	7	APP	_	_\n" +
            "12	بگیرید	_	V	ACT	_	11	ROOT	_	_\n" +
            "13	.	_	PUNC	PUNC	_	12	PUNC	_	_\n" ;
        ConcurrentDependencyGraph graph = parser.Parse(input);
        String actual = graph.toString();

        assertEquals("Failed to stem of '" + input + "'", expected, actual);



        parser.setNormalizer(new Normalizer());
        parser.setLemmatizer(new Lemmatizer());


        String inputSentence = "من به مدرسه رفته بودم.";
        expected = "1	من	من	PR	PR	_	4	SBJ	_	_\n" +
                    "2	به	به	PREP	PREP	_	4	VPP	_	_\n" +
                    "3	مدرسه	مدرسه	N	N	_	2	POSDEP	_	_\n" +
                    "4	رفته بودم	رفت#رو	V	V	_	0	ROOT	_	_\n" +
                    "5	.	.	PUNC	PUNC	_	4	PUNC	_	_\n";
        Iterator<ConcurrentDependencyGraph> iterator = parser.RawParses(Arrays.asList(inputSentence)).iterator();
        graph = iterator.next();
        actual = graph.toString();


        assertEquals("Failed to stem of '" + input + "'", expected, actual);
    }
}