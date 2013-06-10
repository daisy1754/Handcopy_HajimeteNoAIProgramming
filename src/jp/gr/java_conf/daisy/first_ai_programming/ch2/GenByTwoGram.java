package jp.gr.java_conf.daisy.first_ai_programming.ch2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenByTwoGram {
    private static final String IN_FILE_PATH = "data/ch2/2gram.txt";
    private List<String> twoGrams;

    public static void main(String[] args) throws Exception {
        GenByTwoGram twoGramMaker = new GenByTwoGram(IN_FILE_PATH, "Shift-JIS");
        System.out.println("開始文字(日本語一文字)を入力してください");
        Scanner stdInScanner = new Scanner(System.in);
        String ch = stdInScanner.next();
        for (int i = 0; i < 10; i++) {
            System.out.println(twoGramMaker.generate(ch.charAt(0)));
        }
    }

    public GenByTwoGram(String inFilePath) throws Exception {
        this(inFilePath, System.getProperty("file.encoding"));
    }

    public GenByTwoGram(String inFilePath, String charEncoding) throws Exception {
        File file = new File(inFilePath);
        Scanner scanner = new Scanner(file, charEncoding);
        twoGrams = new ArrayList<String>();
        while (scanner.hasNext()) {
            twoGrams.add(scanner.nextLine());
        }
    }

    private int numOfStringsStartedBy(List<String> list, char ch) {
        int num = 0;
        for (String word: list) {
            if (word.charAt(0) == ch) {
                num++;
            }
        }
        return num;
    }

    public  String generate(char startChar) {
        char ch = startChar;
        StringBuilder builder = new StringBuilder();
        builder.append(ch);
        do {
            int numOfStringsStartedBy = numOfStringsStartedBy(twoGrams, ch);
            if (numOfStringsStartedBy == 0) {
                int randomIndex = (int) (Math.random() * twoGrams.size());
                ch = twoGrams.get(randomIndex).charAt(1);
            } else {
                int index = (int) (Math.random() * numOfStringsStartedBy);
                int count = 0;
                for (String word: twoGrams) {
                    if (word.charAt(0) == ch) {
                        if (count == index) {
                            ch = word.charAt(1);
                            break;
                        }
                        count++;
                    }
                }
            }
            builder.append(ch);
        } while (ch != '．' && ch != '。');
        return builder.toString();
    }
}
