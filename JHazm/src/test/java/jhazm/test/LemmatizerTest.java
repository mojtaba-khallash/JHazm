package jhazm.test;

import jhazm.Lemmatizer;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Mojtaba Khallash
 */
public class LemmatizerTest {

    @Test
    public void lemmatizeTest() throws IOException {
        Lemmatizer lemmatizer = new Lemmatizer();

        String input, expected, actual, p;

        List<String> inputs = Arrays.asList(new String[]{
                "کتاب‌ها", "آتشفشان", "می‌روم", "گفته شده است", "نچشیده است", "مردم", "اجتماعی"
        });
        List<String> expecteds = Arrays.asList(new String[]{
                "کتاب", "آتشفشان", "رفت#رو", "گفت#گو", "چشید#چش", "مردم", "اجتماعی"
        });
        List<String> pos = Arrays.asList(new String[]{
                null, null, null, null, null, "N", "AJ"
        });

        for (int i = 0; i < inputs.size(); i++)
        {
            input = inputs.get(i);
            expected = expecteds.get(i);
            p = pos.get(i);
            if (p == null)
                actual = lemmatizer.lemmatize(input);
            else
                actual = lemmatizer.lemmatize(input, p);
            assertEquals("Failed to lematize of '" + input + "' word", expected, actual);
        }
    }

    @Test
    public void conjugationsTest() throws IOException {
        Lemmatizer lemmatizer = new Lemmatizer();

        String input;
        String[] expected;
        List<String> actual;

        input = "خورد#خور";
        expected = new String[] {
                "خوردم", "خوردی", "خورد", "خوردیم", "خوردید", "خوردند",
                "نخوردم", "نخوردی", "نخورد", "نخوردیم", "نخوردید", "نخوردند",
                "خورم", "خوری", /*"خورد",*/ "خوریم", "خورید", "خورند",
                "نخورم", "نخوری", /*"نخورد",*/ "نخوریم", "نخورید", "نخورند",
                "می‌خوردم", "می‌خوردی", /*"می‌خورد",*/ "می‌خوردیم", "می‌خوردید", "می‌خوردند",
                "نمی‌خوردم", "نمی‌خوردی", "نمی‌خورد", "نمی‌خوردیم", "نمی‌خوردید", "نمی‌خوردند",
                "خورده‌ام", "خورده‌ای", "خورده", "خورده‌ایم", "خورده‌اید", "خورده‌اند",
                "نخورده‌ام", "نخورده‌ای", "نخورده", "نخورده‌ایم", "نخورده‌اید", "نخورده‌اند",
                "می‌خورم", "می‌خوری", "می‌خورد", "می‌خوریم", "می‌خورید", "می‌خورند",
                "نمی‌خورم", "نمی‌خوری", /*"نمی‌خورد",*/ "نمی‌خوریم", "نمی‌خورید", "نمی‌خورند",
                "بخورم", "بخوری", "بخورد", "بخوریم", "بخورید", "بخورند",
                "بخور", "نخور"
        };
        actual = lemmatizer.conjugations(input);
        check(input, expected, actual);

        input = "آورد#آور";
        expected = new String[] {
                "آوردم", "آوردی", "آورد", "آوردیم", "آوردید", "آوردند",
                "نیاوردم", "نیاوردی", "نیاورد", "نیاوردیم", "نیاوردید", "نیاوردند",
                "آورم", "آوری", /*"آورد",*/ "آوریم", "آورید", "آورند",
                "نیاورم", "نیاوری", /*"نیاورد",*/ "نیاوریم", "نیاورید", "نیاورند",
                "می‌آوردم", "می‌آوردی", /*"می‌آورد",*/ "می‌آوردیم", "می‌آوردید", "می‌آوردند",
                "نمی‌آوردم", "نمی‌آوردی", "نمی‌آورد", "نمی‌آوردیم", "نمی‌آوردید", "نمی‌آوردند",
                "آورده‌ام", "آورده‌ای", "آورده", "آورده‌ایم", "آورده‌اید", "آورده‌اند",
                "نیاورده‌ام", "نیاورده‌ای", "نیاورده", "نیاورده‌ایم", "نیاورده‌اید", "نیاورده‌اند",
                "می‌آورم", "می‌آوری", "می‌آورد", "می‌آوریم", "می‌آورید", "می‌آورند",
                "نمی‌آورم", "نمی‌آوری", /*"نمی‌آورد",*/ "نمی‌آوریم", "نمی‌آورید", "نمی‌آورند",
                "بیاورم", "بیاوری", "بیاورد", "بیاوریم", "بیاورید", "بیاورند",
                "بیاور", "نیاور"
        };
        actual = lemmatizer.conjugations(input);
        check(input, expected, actual);
    }

    private void check(String input, String[] expected, List<String> actual) {
        assertEquals("Failed to generate conjugations of '" + input + "' verb", expected.length, actual.size());
        for (int i = 0; i < expected.length; i++) {
            if (!actual.contains(expected[i]))
                assertEquals("Failed to generate conjugations of '" + input + "' verb", expected[i], actual.get(i));
        }
    }
}