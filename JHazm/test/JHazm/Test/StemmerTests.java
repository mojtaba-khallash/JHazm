package JHazm.Test;

import JHazm.Stemmer;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Mojtaba Khallash
 */
public class StemmerTests {
    
    @Test
    public void StemTest() {
        Stemmer stemmer = new Stemmer();

        String input, expected, actual;

        input = "کتابی";
        expected = "کتاب";
        actual = stemmer.Stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "کتاب‌ها";
        expected = "کتاب";
        actual = stemmer.Stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "کتاب‌هایی";
        expected = "کتاب";
        actual = stemmer.Stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "کتابهایشان";
        expected = "کتاب";
        actual = stemmer.Stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "اندیشه‌اش";
        expected = "اندیشه";
        actual = stemmer.Stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);
    }
}