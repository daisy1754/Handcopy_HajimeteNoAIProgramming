package jp.gr.java_conf.daisy.first_ai_programming.ch2;

import java.io.File;
import java.util.*;

public class SortN {
    private static final String IN_FILE_PATH =
            "/out/std_out.txt";

    public static void main(String[] args) throws Exception {
        File inFile = new File(IN_FILE_PATH);
        Scanner scanner = new Scanner(inFile);
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        // sort
        Collections.sort(lines);
        // count
        final Map<String, Integer> occurrence = new HashMap<String, Integer>();
        for (Iterator<String> itr = lines.iterator(); itr.hasNext(); ) {
            String line = itr.next();
            if (occurrence.containsKey(line)) {
                occurrence.put(line, occurrence.get(line) + 1);
                itr.remove();
            } else {
                occurrence.put(line, 1);
            }
        }
        // sort by occurrence
        Collections.sort(lines, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return occurrence.get(s2) - occurrence.get(s1);
            }
        });
        for (String line: lines) {
            System.out.println(occurrence.get(line) + "\t" + line);
        }
    }
}
