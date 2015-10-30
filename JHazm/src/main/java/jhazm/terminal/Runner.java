package jhazm.terminal;

import edu.stanford.nlp.ling.TaggedWord;
import jhazm.*;
import jhazm.tokenizer.WordTokenizer;
import org.apache.commons.cli.*;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.maltparser.concurrent.graph.ConcurrentDependencyGraph;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by majid on 12/31/14.
 */
public class Runner {
    public static void main(String[] args) throws IOException {
        // create the Options
        Options options = new Options();
        options.addOption("a", "action", true, "action to do. " + StringUtils.join(Action.values(), ", ") + ", are the options.");
        options.addOption("i", "input", true, "input, standard input file file.");
        options.addOption("t", "text", true, "input text");
        options.addOption("o", "output", true, "output, standard output file.");
        options.addOption("v", "verbose", true, "show output string on console. console may not support UTF-8 in some operating systems.");

        CommandLineParser parser = new BasicParser();
        Action action = null;
        Path inputPath, outputPath = null;
        String inputText = null;
        boolean verbose = true;
        try {
            CommandLine line = parser.parse(options, args);
            if (!line.hasOption("a"))
                showHelp(options);
            try {
                action = Action.valueOf(line.getOptionValue("a"));
            } catch (Exception exp) {
                System.err.println("wrong action.");
                System.exit(1);
            }
            if (action == null) showHelp(options);
            String inputFile = null;
            if (!line.hasOption("i") && !line.hasOption("t")) {
                inputFile = "resources/sample.txt";
            }
            if (line.hasOption("i")) {
                inputFile = line.getOptionValue("i");
            }

            if (((inputFile == null && !line.hasOption("t")) || !line.hasOption("o")))
                showHelp(options);
            if (inputFile != null) {
                inputPath = Paths.get(inputFile);
                if (!Files.exists(inputPath)) {
                    System.err.println("file does not exists: " + inputPath.toFile().getAbsolutePath());
                    System.exit(1);
                } else {
                    System.err.println("input file: " + inputPath.toFile().getAbsolutePath());
                    byte[] encoded = Files.readAllBytes(inputPath);
                    inputText = new String(encoded, "UTF-8");
                }
            }
            if (line.hasOption("t")) inputText = line.getOptionValue("t");
            if (line.hasOption("o")) {
                outputPath = Paths.get(line.getOptionValue("o"));
                System.err.println("output path is: " + outputPath.toFile().getAbsolutePath());
            }
            if (line.hasOption("v")) verbose = line.getOptionValue("v").equals("true");
        } catch (ParseException exp) {
            System.err.println(exp);
            showHelp(options);
        }

        assert action != null;
        assert outputPath != null;
        Normalizer normalizer = new Normalizer();
        inputText = normalizer.run(inputText);
        WordTokenizer tokenizer = new WordTokenizer();
        List<String> tokens = tokenizer.tokenize(inputText);
        StringBuilder builder = new StringBuilder();
        System.err.println("working directory: " + System.getProperty("user.dir"));
        try {
            switch (action) {
                case stemming:
                    System.err.println("stemming, text = " + inputText);
                    Stemmer stemmer = new Stemmer();
                    for (String token : tokens) {
                        String stem = stemmer.stem(token);
                        if (verbose) System.err.println(stem);
                        builder.append(stem).append(' ');
                    }
                    String stem = stemmer.stem(inputText);
                    if (verbose) System.err.println(stem);
                    Files.write(outputPath, stem.getBytes("UTF-8"));
                    break;
                case normalizing:
                    System.err.println("notmalizing, text = " + inputText);
                    if (verbose) System.err.println(inputText);
                    Files.write(outputPath, inputText.getBytes("UTF-8"));
                    break;
                case workTokenizing:
                    System.err.println("tokenizing, text = " + inputText);
                    String tokenized = StringUtils.join(tokens, " ");
                    if (verbose) System.err.println(tokenized);
                    assert tokenized != null;
                    Files.write(outputPath, tokenized.getBytes("UTF-8"));
                    break;
                case sentenceTokenizing:
                    System.err.println("tokenizing, text = " + inputText);
                    tokenized = StringUtils.join(tokens, " ");
                    if (verbose) System.err.println(tokenized);
                    assert tokenized != null;
                    Files.write(outputPath, tokenized.getBytes("UTF-8"));
                    break;
                case lemmatize:
                    System.err.println("lemmatize, text = " + inputText);
                    Lemmatizer lemmatizer = new Lemmatizer();
                    for (String token : tokens) {
                        String lemma = lemmatizer.lemmatize(token);
                        if (verbose) System.err.println(lemma);
                        builder.append(lemma).append(' ');
                    }
                    Files.write(outputPath, builder.toString().getBytes("UTF-8"));
                    break;
                case partOfSpeechTagging:
                    System.err.println("part of speech tagging, text = " + inputText);
                    POSTagger posTagger = new POSTagger();
                    List<TaggedWord> tagged = posTagger.batchTag(tokens);
                    if (verbose) {
                        for (TaggedWord taggedWord : tagged) {
                            System.err.println(taggedWord.word() + "\t" + taggedWord.tag());
                            builder.append(taggedWord.word()).append("\t").append(taggedWord.tag()).append("\r\n");
                        }
                    }
                    Files.write(outputPath, builder.toString().getBytes("UTF-8"));
                    break;
                case dependencyParsing:
                    System.err.println("dependency parser, text = " + inputText);
                    DependencyParser dependencyParser = new DependencyParser();
                    posTagger = new POSTagger();
                    tagged = posTagger.batchTag(tokens);
                    ConcurrentDependencyGraph graph = dependencyParser.rawParse(tagged);
                    String output = graph.toString();
                    System.err.println(output);
                    Files.write(outputPath, output.getBytes("UTF-8"));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        final StringBuilder helpBuilder = new StringBuilder().append('\n');
        helpBuilder.append("Welcome to JHazm.").append('\n');
        PrintWriter pw = new PrintWriter(new StringBuilderWriter(helpBuilder));
        formatter.printHelp(pw, 80, "java -jar jhazm.jar", null,
                options, 0, 0, "Thank you", false);
        helpBuilder.append("Required options for stemmer: --i or --t, --o").append('\n');
        System.err.println(helpBuilder);
        System.exit(0);
    }
}
