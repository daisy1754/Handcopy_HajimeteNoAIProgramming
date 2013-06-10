package jp.gr.java_conf.daisy.first_ai_programming.ch2;

import java.util.Scanner;

public class TwoGramAI {
    private static final String TWO_GRAM_DATA_FILE
            = "data/ch2/two_gram_input_from_wikipedia.txt";

    public static void main(String[] args) throws Exception {
        GenByTwoGram genByTwoGram = new GenByTwoGram(TWO_GRAM_DATA_FILE);
        System.out.println("さくら: メッセージをどうぞ");
        System.out.print("あなた: ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            int index = (int) (Math.random() * input.length());
            System.out.print("さくら: ");
            System.out.println(genByTwoGram.generate(input.charAt(index)));
            System.out.print("あなた: ");
        }
        System.out.println("さくら: ばいばーい");
    }
}
