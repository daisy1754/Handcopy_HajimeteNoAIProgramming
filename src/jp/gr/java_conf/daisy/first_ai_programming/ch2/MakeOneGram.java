package jp.gr.java_conf.daisy.first_ai_programming.ch2;

import java.util.Scanner;

public class MakeOneGram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MakeNGram makeNGram = new MakeNGram(2);
        String inputs = makeNGram.readStdIn(scanner);
        String target = makeNGram.filterTwoByteCharacters(inputs);
        makeNGram.outputText(target);
    }
}
