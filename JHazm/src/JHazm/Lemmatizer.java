package JHazm;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class Lemmatizer {
    private HashMap verbs;
    private HashSet<String> words;

    public Lemmatizer() throws IOException { 
        this("data/words.dat", "data/verbs.dat", true);
    }
    public Lemmatizer(boolean joinedVerbParts) throws IOException {
        this("data/words.dat", "data/verbs.dat", joinedVerbParts);
    }
    public Lemmatizer(String wordsFile, String verbsFile) throws IOException {
        this(wordsFile, verbsFile, true);
    }
    public Lemmatizer(String wordsFile, String verbsFile, boolean joinedVerbParts)
            throws IOException {
        //Stemmer stemmer = new Stemmer();

        this.words = new HashSet<>();
        for (String line : Files.readAllLines(Paths.get(wordsFile), Charset.forName("UTF8")))
            this.words.add(line.trim());

        WordTokenizer tokenizer = new WordTokenizer(verbsFile);

        List<String> pureVerbs = Files.readAllLines(Paths.get(verbsFile), Charset.forName("UTF8"));

        this.verbs = new HashMap();
        this.verbs.put("است", "#است");
        for (String verb : pureVerbs) {
            for (String tense : Conjugations(verb)) {
                if (!this.verbs.containsKey(tense))
                    this.verbs.put(tense, verb);
            }
        }

        if (joinedVerbParts) {
            for (String verb : pureVerbs) {
                String[] parts = verb.split("#");
                for (String afterVerb : tokenizer.getAfterVerbs()) {
                    this.verbs.put(parts[0] + "ه " + afterVerb, verb);
                }
                for (String beforeVerb : tokenizer.getBeforeVerbs()) {
                    this.verbs.put(beforeVerb + " " + parts[0], verb);
                }
            }
        }
    }

    public String Lemmatize(String word) {
        return Lemmatize(word, "");
    }
    
    public String Lemmatize(String word, String pos) {
        if ((pos.length() == 0 || pos.equals("V")) && this.verbs.containsKey(word))
            return this.verbs.get(word).toString();

        if (this.words.contains(word))
            return word;

        String stem = new Stemmer().Stem(word);
        if (this.words.contains(stem))
            return stem;

        return word;
    }

    public List<String> Conjugations(String verb) {
        String[] endsList = new String[] { "م", "ی", "", "یم", "ید", "ند" };
        List<String> ends = new ArrayList<>(Arrays.asList(endsList));

        if (verb.equals("#هست")) {
            List<String> conjugate1 = new ArrayList<>();
            List<String> conjugate2 = new ArrayList<>();
            for (String end : ends) {
                conjugate1.add("هست" + end);
                conjugate2.add("نیست" + end);
            }
            conjugate1.addAll(conjugate2);
            return conjugate1;
        }

        HashSet<String> conjugates = new HashSet<>();
        String[] parts = verb.split("#");
        String past = parts[0];
        String present = parts[1];

        for (String end : ends) {
            String conj = past + end;
            String nconj;

            // pastSimples
            conj = GetRefinement(conj);
            conjugates.add(conj);
            nconj = GetRefinement(GetNot(conj));
            conjugates.add(nconj);


            conj = "می‌" + conj;

            // pastImperfects
            conj = GetRefinement(conj);
            conjugates.add(conj);
            nconj = GetRefinement(GetNot(conj));
            conjugates.add(nconj);
        }

        endsList = new String[] { "ه‌ام", "ه‌ای", "ه", "ه‌ایم", "ه‌اید", "ه‌اند" };
        ends = new ArrayList<>(Arrays.asList(endsList));

        // pastNarratives
        for (String end : ends) {
            String conj = past + end;
            conjugates.add(GetRefinement(conj));
            conjugates.add(GetRefinement(GetNot(conj)));
        }

        conjugates.add(GetRefinement("ب" + present));
        conjugates.add(GetRefinement("ن" + present));

        if (present.endsWith("ا") || Arrays.asList(new String[] { "آ", "گو" }).contains(present))
            present = present + "ی";

        endsList = new String[] { "م", "ی", "د", "یم", "ید", "ند" };
        ends = new ArrayList<>(Arrays.asList(endsList));

        List<String> presentSimples = new ArrayList<>();
        for (String end : ends) {
            String conj = present + end;
            presentSimples.add(conj);

            conjugates.add(GetRefinement(conj));
            conjugates.add(GetRefinement(GetNot(conj)));
        }

        for (String item : presentSimples) {
            String conj;

            // presentImperfects
            conj = "می‌" + item;
            conjugates.add(GetRefinement(conj));
            conjugates.add(GetRefinement(GetNot(conj)));

            // presentSubjunctives
            conj = "ب" + item;
            conjugates.add(GetRefinement(conj));

            // presentNotSubjunctives
            conj = "ن" + item;
            conjugates.add(GetRefinement(conj));
        }

        return new ArrayList(conjugates);
    }

    private String GetRefinement(String text) {
        return text.replace("بآ", "بیا").replace("نآ", "نیا");
    }

    private String GetNot(String text) {
        return "ن" + text;
    }
}