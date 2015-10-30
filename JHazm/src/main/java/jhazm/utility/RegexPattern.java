package jhazm.utility;

/**
 *
 * @author Mojtaba Khallash
 */
public class RegexPattern {
    private final String Pattern;
    private final String Replace;
    public RegexPattern(String pattern, String replace) {
        this.Pattern = pattern;
        this.Replace = replace;
    }

    public String apply(String text) {
        return text.replaceAll(Pattern, Replace);
    }
}