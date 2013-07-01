package jp.gr.java_conf.daisy.first_ai_programming.ch5;

import java.util.HashSet;
import java.util.Set;

/**
 * Production rule used in {@link RuleBasedCounselor}.
 * Contains a set of keywords and answer statement to an input that contains
 * these keywords.
 */
public class AnsweringRule {
    final Set<String> keywords;
    final String answerStatement;

    /**
     * @param keywords Set of keywords
     * @param answerStatement Statement that can be used as answer when
     *                        input contains all of the keywords.
     */
    AnsweringRule(Set<String> keywords, String answerStatement) {
        this.keywords = new HashSet<String>(keywords);
        this.answerStatement = answerStatement;
    }

    /**
     * @return if all keywords of this rule is included by input.
     */
    public boolean allKeywordsAreIncludedIn(String input) {
        for (String keyword: keywords) {
            if (!input.contains(keyword)) {
                return false;
            }
        }
        return true;
    }

    public String getAnswerStatement() {
        return answerStatement;
    }
}
