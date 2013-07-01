package jp.gr.java_conf.daisy.first_ai_programming.ch5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * ai5.c (p. 175-)に対応
 *
 * This program answers user's input using given rules:
 * 1) If input line contains all the keywords of the certain rule, the program
 * outputs the result statement of the rule. If there are multiple rule that
 * satisfy this, this program choose one rule from them.
 * 2) If there is no rule that satisfy the condition, the program outputs
 * "どうぞ続けてください".
 */
public class RuleBasedCounselor {
    private static final String INITIAL_STATEMENT = "さて，どうしました?";
    private static final String DEFAULT_OUTPUT_STATEMENT = "どうぞ続けてください";
    private static final String COUNSELOR_NAME = "さくら";
    private static final String YOUR_NAME = "あなた";

    private Set<AnsweringRule> rules;

    /**
     * Note: first argument must be the path to a rule file. Each line of the
     * rule file represents a production rule. A production rule consists of
     * four keywords and one result statement separated by a space. If number of
     * keywords is less than four, hyphen "-" is used as a blank keyword.
     */
    public static void main(String[] args) {
        RuleBasedCounselor counselor = new RuleBasedCounselor();
        counselor.parseRules(args[0]);
        System.out.println(COUNSELOR_NAME + ": " + INITIAL_STATEMENT);
        System.out.print(YOUR_NAME + ": ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            System.out.println(COUNSELOR_NAME + ": "
                    + counselor.searchPossibleAnswer(input));
            System.out.print(YOUR_NAME + ": ");
        }
    }

    public void parseRules(String ruleFilePath) {
        rules = new HashSet<AnsweringRule>();
        File ruleFile = new File(ruleFilePath);
        Scanner ruleScanner;
        try {
            ruleScanner = new Scanner(new FileInputStream(ruleFile));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Rule file '" + ruleFilePath
                    + "' is not found", e);
        }
        while (ruleScanner.hasNext()) {
            String line = ruleScanner.nextLine();
            String[] keywordsAndAnswer = line.split("\\s");
            Set<String> keywords = new HashSet<String>(Arrays.asList(Arrays.copyOf(
                    keywordsAndAnswer, keywordsAndAnswer.length - 1)));
            // We ignore '-' as invalid keyword
            Util.removeIfContains(keywords, "-");
            String answer = keywordsAndAnswer[keywordsAndAnswer.length - 1];
            rules.add(new AnsweringRule(keywords, answer));
        }
        ruleScanner.close();
    }

    public String searchPossibleAnswer(String input) {
        Set<String> possibleAnswers = searchPossibleAnswers(input);
        if (possibleAnswers.isEmpty()) {
            return DEFAULT_OUTPUT_STATEMENT;
        } else {
            return Util.getRandomElementFrom(possibleAnswers);
        }
    }

    private Set<String> searchPossibleAnswers(String input) {
        if (rules == null) {
            throw new IllegalStateException(
                    "You must first initialize answering rules.");
        }
        Set<String> possibleAnswers = new HashSet<String>();
        for (AnsweringRule rule: rules) {
            if (rule.allKeywordsAreIncludedIn(input)) {
                possibleAnswers.add(rule.getAnswerStatement());
            }
        }
        return possibleAnswers;
    }
}
