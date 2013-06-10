package jp.gr.java_conf.daisy.first_ai_programming.ch2;

import java.util.Scanner;

public class MakeNGram {
    private int nGram;

    public MakeNGram(int nGram) {
        this.nGram = nGram;
    }

    public String readStdIn(Scanner scanner) {
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine()).append(System.lineSeparator());
        }
        return builder.toString();
    }

    public String filterTwoByteCharacters(String string) {
        StringBuilder builder = new StringBuilder();
        char[] chars = string.toCharArray();
        for (int i= 0; i < chars.length; i++) {
            char c = chars[i];
            if ((c <= '\u007e') || // 英数字
                    (c == '\u00a5') || // \記号
                    (c == '\u203e') || // ~記号
                    (c >= '\uff61' && c <= '\uff9f') // 半角カナ
                    ) {
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    public void outputText(String string) {
        for (int i = 0; i < string.length() - nGram + 1; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < nGram; j++) {
                builder.append(string.charAt(i + j));
            }
            System.out.println(builder.toString());
        }
    }
}
