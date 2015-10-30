package jhazm.test;

import jhazm.Stemmer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Mojtaba Khallash
 */
public class StemmerTests {
    
    @Test
    public void stemTest() {
        Stemmer stemmer = new Stemmer();

        String input, expected, actual;

        input = "کتابی";
        expected = "کتاب";
        actual = stemmer.stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "کتاب‌ها";
        expected = "کتاب";
        actual = stemmer.stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "کتاب‌هایی";
        expected = "کتاب";
        actual = stemmer.stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "کتابهایشان";
        expected = "کتاب";
        actual = stemmer.stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);

        input = "اندیشه‌اش";
        expected = "اندیشه";
        actual = stemmer.stem(input);
        assertEquals("Failed to stem of '" + input + "'", expected, actual);
    }
}