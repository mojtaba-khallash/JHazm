package JHazm;

import JHazm.Utility.RegexPattern;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class WordTokenizer {
    private boolean joinVerbParts = true;
    private HashSet<String> beforeVerbs;
    public HashSet<String> getBeforeVerbs() {
        return beforeVerbs;
    }
    private HashSet<String> afterVerbs;
    public HashSet<String> getAfterVerbs() {
        return afterVerbs;
    }

    private RegexPattern pattern;

    List<String> verbs;
    public List<String> getVerbs() {
        return verbs;
    }

    public WordTokenizer() throws IOException {
        this(true);
    }

    public WordTokenizer(boolean joinVerbParts) throws IOException {
        this("data/verbs.dat", joinVerbParts);
    }

    public WordTokenizer(String verbsFile) throws IOException {
        this(verbsFile, true);
    }

    public WordTokenizer(String verbsFile, boolean joinVerbParts)
            throws IOException {
        this.joinVerbParts = joinVerbParts;
        this.pattern = new RegexPattern("([؟!\\?]+|[:\\.،؛»\\]\\)\\}\"«\\[\\(\\{])", " $1 ");

        if (this.joinVerbParts) {
            String[] tokens;

            tokens = new String[] {
                "ام", "ای", "است", "ایم", "اید", "اند", 
                "بودم", "بودی", "بود", "بودیم", "بودید", "بودند", 
                "باشم", "باشی", "باشد", "باشیم", "باشید", "باشند",
                "شده ام", "شده ای", "شده است", "شده ایم", "شده اید", "شده اند", 
                "شده بودم", "شده بودی", "شده بود", "شده بودیم", "شده بودید", "شده بودند", 
                "شده باشم", "شده باشی", "شده باشد", "شده باشیم", "شده باشید", "شده باشند",
                "نشده ام", "نشده ای", "نشده است", "نشده ایم", "نشده اید", "نشده اند", 
                "نشده بودم", "نشده بودی", "نشده بود", "نشده بودیم", "نشده بودید", "نشده بودند", 
                "نشده باشم", "نشده باشی", "نشده باشد", "نشده باشیم", "نشده باشید", "نشده باشند",
                "شوم", "شوی", "شود", "شویم", "شوید", "شوند", 
                "شدم", "شدی", "شد", "شدیم", "شدید", "شدند",
                "نشوم", "نشوی", "نشود", "نشویم", "نشوید", "نشوند", 
                "نشدم", "نشدی", "نشد", "نشدیم", "نشدید", "نشدند",
                "می‌شوم", "می‌شوی", "می‌شود", "می‌شویم", "می‌شوید", "می‌شوند", 
                "می‌شدم", "می‌شدی", "می‌شد", "می‌شدیم", "می‌شدید", "می‌شدند",
                "نمی‌شوم", "نمی‌شوی", "نمی‌شود", "نمی‌شویم", "نمی‌شوید", "نمی‌شوند", 
                "نمی‌شدم", "نمی‌شدی", "نمی‌شد", "نمی‌شدیم", "نمی‌شدید", "نمی‌شدند",
                "خواهم شد", "خواهی شد", "خواهد شد", "خواهیم شد", "خواهید شد", "خواهند شد",
                "نخواهم شد", "نخواهی شد", "نخواهد شد", "نخواهیم شد", "نخواهید شد", "نخواهند شد"
            };

            this.afterVerbs = new HashSet<>(Arrays.asList(tokens));

            tokens = new String[] {
                "خواهم", "خواهی", "خواهد", "خواهیم", "خواهید", "خواهند",
                "نخواهم", "نخواهی", "نخواهد", "نخواهیم", "نخواهید", "نخواهند"
            };

            this.beforeVerbs = new HashSet<>(Arrays.asList(tokens));

            this.verbs = new ArrayList<>(Files.readAllLines(Paths.get(verbsFile), Charset.forName("UTF8")));
            Collections.reverse(this.verbs);
            for (int i = 0; i < this.verbs.size(); i++)
            {
                String verb = this.verbs.get(i);
                this.verbs.set(i, verb.trim().split("#")[0] + "ه");
            }
        }
    }

    public List<String> Tokenize(String sentence) {
        sentence = this.pattern.Apply(sentence).trim();
        List<String> tokens = Arrays.asList(sentence.split(" +"));
        if (this.joinVerbParts)
            tokens = this.JoinVerbParts(tokens);
        return tokens;
    }

    private List<String> JoinVerbParts(List<String> tokens) {
        Collections.reverse(tokens);
        List<String> newTokens = new ArrayList<>();

        for (String token : tokens) {
            if (newTokens.size() > 0) {
                String lastWord = newTokens.get(newTokens.size() - 1);
                if (this.beforeVerbs.contains(token) ||
                    (this.afterVerbs.contains(lastWord) && this.verbs.contains(token))) {
                    lastWord = token + " " + lastWord;
                    newTokens.set(newTokens.size() - 1, lastWord);
                }
                else
                    newTokens.add(token);
            }
            else
                newTokens.add(token);
        }

        Collections.reverse(newTokens);
        return newTokens;
    }
}