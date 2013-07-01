package jp.gr.java_conf.daisy.first_ai_programming.ch5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * makesnet.c (p. 162-)に対応
 *
 * This program outputs is-a association from given word.
 * Is a relationships should be given as a text file.
 */
public class AssociationWithSemanticNetwork {
    private static final String START_SENTENCE
            = "連想を開始する単語を入力してください";

    /**
     * Note: first argument must be the path to the file that specify is-a
     * relationships between words. Given E[n] as a word of the n-th line,
     * a file with 2n line represents n is-a relationships; E[2k-1] is a E[2k].
     */
    public static void main(String[] args) {
        AssociationEngine engine = new AssociationEngine();
        engine.parseIsARelationsFromFile(args[0]);
        Scanner scanner = new Scanner(System.in);
        System.out.println(START_SENTENCE);
        while (scanner.hasNext()) {
            engine.outputRandomAssociations(scanner.nextLine());
            System.out.println(START_SENTENCE);
        }
    }

    static private class AssociationEngine {
        private Map<String, List<String>> isAMap;

        public void parseIsARelationsFromFile(String filePath) {
            FileInputStream stream;
            try {
                stream= new FileInputStream(new File(filePath));
            } catch (FileNotFoundException e) {
                throw new IllegalStateException(
                        "Infile '" + filePath + "' not found", e);
            }
            isAMap = new HashMap<String, List<String>>();
            Scanner scanner = new Scanner(stream);
            int numOfReadLine = 0;
            String key = null;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (numOfReadLine % 2 == 0) {
                    key = line;
                } else {
                    if (!isAMap.containsKey(key)) {
                        isAMap.put(key, new ArrayList<String>());
                    }
                    isAMap.get(key).add(line);
                }
                numOfReadLine++;
            }
        }

        public void outputRandomAssociations(String startWord) {
            if (isAMap == null) {
                throw new IllegalStateException(
                        "You must initialize is-a relations before use");
            }
            while (isAMap.containsKey(startWord)) {
                List<String> isAs = isAMap.get(startWord);
                String isA = Util.getRandomElementFrom(isAs);
                System.out.println(startWord + " は " + isA);
                isAs.remove(isA);
                if (isAs.size() == 0) {
                    isAMap.remove(startWord);
                }
                startWord = isA;
            }
            System.out.println(startWord + " は …わからない!");
        }
    }
}
