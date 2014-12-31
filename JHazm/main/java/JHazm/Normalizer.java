package JHazm;

import JHazm.Utility.MakeTrans;
import JHazm.Utility.RegexPattern;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class Normalizer {
    private boolean characterRefinement = true;
    private List<RegexPattern> characterRefinementPatterns;

    private boolean punctuationSpacing = true;
    private List<RegexPattern> punctuationSpacingPatterns;

    private boolean affixSpacing = true;
    private List<RegexPattern> affixSpacingPatterns;

    private final String puncAfter = "!:\\.،؛؟»\\]\\)\\}";
    private final String puncBefore = "«\\[\\(\\{";

    private MakeTrans translations;

    public Normalizer() {
        this(true, true, true);
    }

    public Normalizer(boolean characterRefinement, boolean punctuationSpacing, boolean affixSpacing) {
        this.characterRefinement = characterRefinement;
        this.punctuationSpacing = punctuationSpacing;
        this.affixSpacing = affixSpacing;

        this.translations = new MakeTrans(" كي;%1234567890", " کی؛٪۱۲۳۴۵۶۷۸۹۰");


        if (this.characterRefinement) {
            this.characterRefinementPatterns = new ArrayList<>();
            // remove "keshide" and "carriage return" characters
            this.characterRefinementPatterns.add(new RegexPattern("[ـ\\r]", ""));
            // remove extra spaces
            this.characterRefinementPatterns.add(new RegexPattern(" +", " "));
            // remove extra newlines
            this.characterRefinementPatterns.add(new RegexPattern("\n\n+", "\n\n"));
            // replace 3 dots
            this.characterRefinementPatterns.add(new RegexPattern(" ?\\.\\.\\.", " …"));
        }

        if (this.punctuationSpacing) {
            this.punctuationSpacingPatterns = new ArrayList<>();
            // remove space before punctuation
            this.punctuationSpacingPatterns.add(new RegexPattern(" ([" + puncAfter + "])", "$1"));
            // remove space after punctuation
            this.punctuationSpacingPatterns.add(new RegexPattern("([" + puncBefore + "]) ", "$1"));
            // put space after
            this.punctuationSpacingPatterns.add(new RegexPattern("([" + puncAfter + "])([^ " + puncAfter + "])", "$1 $2"));
            // put space before
            this.punctuationSpacingPatterns.add(new RegexPattern("([^ " + puncBefore + "])([" + puncBefore + "])", "$1 $2"));
        }

        if (this.affixSpacing) {
            this.affixSpacingPatterns = new ArrayList<>();
            // fix ی space
            this.affixSpacingPatterns.add(new RegexPattern("([^ ]ه) ی ", "$1‌ی "));
            // put zwnj after می, نمی
            this.affixSpacingPatterns.add(new RegexPattern("(^| )(ن?می) ", "$1$2‌"));
            // put zwnj before تر, ترین, ها, های
            this.affixSpacingPatterns.add(new RegexPattern(" (تر(ی(ن)?)?|ها(ی)?)(?=[ \n" + puncAfter + puncBefore + "]|$)", "‌$1"));
            // join ام, ات, اش, ای
            this.affixSpacingPatterns.add(new RegexPattern("([^ ]ه) (ا(م|ت|ش|ی))(?=[ \n" + puncAfter + "]|$)", "$1‌$2"));
        }
    }

    public String Run(String text) {
        if (this.characterRefinement)
            text = CharacterRefinement(text);

        if (this.punctuationSpacing)
            text = PunctuationSpacing(text);

        if (this.affixSpacing)
            text = AffixSpacing(text);

        return text;
    }

    private String CharacterRefinement(String text) {
        text = this.translations.Translate(text);
        for (RegexPattern pattern : this.characterRefinementPatterns)
            text = pattern.Apply(text);
        return text;
    }

    private String PunctuationSpacing(String text) {
        // TODO: don't put space inside time and float numbers
        for (RegexPattern pattern : this.punctuationSpacingPatterns)
            text = pattern.Apply(text);
        return text;
    }

    private String AffixSpacing(String text) {
        for (RegexPattern pattern : this.affixSpacingPatterns)
            text = pattern.Apply(text);
        return text;
    }
}