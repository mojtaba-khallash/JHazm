package JHazm.Test;

import JHazm.Normalizer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Mojtaba Khallash
 */
public class NormalizerTests {
    
    @Test
    public void CharacterRefinementTest() {
        Normalizer normalizer = new Normalizer(true, false, false);

        String input, expected, actual;

        input = "اصلاح كاف و ياي عربي";
        expected = "اصلاح کاف و یای عربی";
        actual = normalizer.Run(input);
        assertEquals("Failed to character refinement of '" + input + "'", expected, actual);

        input = "رمــــان";
        expected = "رمان";
        actual = normalizer.Run(input);
        assertEquals("Failed to character refinement of '" + input + "'", expected, actual);

        input = "1,2,3,...";
        expected = "۱,۲,۳, …";
        actual = normalizer.Run(input);
        assertEquals("Failed to character refinement of '" + input + "'", expected, actual);
    }

    @Test
    public void PunctuationSpacing() {
        Normalizer normalizer = new Normalizer(false, true, false);

        String input, expected, actual;

        input = "اصلاح ( پرانتزها ) در متن .";
        expected = "اصلاح (پرانتزها) در متن.";
        actual = normalizer.Run(input);
        assertEquals("Failed to punctuation spacing of '" + input + "'", expected, actual);
    }

    @Test
    public void AffixSpacing() {
        Normalizer normalizer = new Normalizer(false, false, true);

        String input, expected, actual;

        input = "خانه ی پدری";
        expected = "خانه‌ی پدری";
        actual = normalizer.Run(input);
        assertEquals("Failed to affix spacing of '" + input + "'", expected, actual);

        input = "فاصله میان پیشوند ها و پسوند ها را اصلاح می کند.";
        expected = "فاصله میان پیشوند‌ها و پسوند‌ها را اصلاح می‌کند.";
        actual = normalizer.Run(input);
        assertEquals("Failed to affix spacing of '" + input + "'", expected, actual);

        input = "می روم";
        expected = "می‌روم";
        actual = normalizer.Run(input);
        assertEquals("Failed to affix spacing of '" + input + "'", expected, actual);

        input = "حرفه ای";
        expected = "حرفه‌ای";
        actual = normalizer.Run(input);
        assertEquals("Failed to affix spacing of '" + input + "'", expected, actual);
    }
}