package JHazm;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Mojtaba Khallash
 */
public class Stemmer {
    private final String[] ends = new String[] {
        "ات", "ان", "ترین", "تر", "م", "ت", "ش", "یی", "ی", "ها", "ٔ", "‌ا", //
    };

    public String Stem(String word) {
        for (String end : this.ends) {
            if (word.endsWith(end)) {
                word = word.substring(0, word.length() - end.length()).trim();
                word = StringUtils.strip(word, "‌");
            }
        }

        if (word.endsWith("ۀ"))
            word = word.substring(0, word.length() - 1) + "ه";

        return word;
    }
}