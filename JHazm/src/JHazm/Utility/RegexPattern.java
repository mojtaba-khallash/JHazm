package JHazm.Utility;

/**
 *
 * @author Mojtaba Khallash
 */
public class RegexPattern {
    public RegexPattern(String pattern, String replace) {
        this.Pattern = pattern;
        this.Replace = replace;
    }

    private final String Pattern;
    private final String Replace;

    public String Apply(String text) {
        return text.replaceAll(Pattern, Replace);
    }
}