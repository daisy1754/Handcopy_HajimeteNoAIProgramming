package jp.gr.java_conf.daisy.first_ai_programming.ch1;

import java.util.Scanner;

public class EchoAI {
    public static void main(String[] args) {
        System.out.println("さくら: メッセージをどうぞ");
        System.out.print("あなた: ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            scanner.nextLine();
            System.out.println("さくら: ふーん，それで？");
            System.out.print("あなた: ");
        }
        System.out.println("さくら: ばいばーい");
    }
}
