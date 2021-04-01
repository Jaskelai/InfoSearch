package com.github.jaskelai.infosearch;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static void writeToFileLineByLine(String path, List<String> lines) {
        File fout = new File(path);
        if (!fout.exists()) {
            fout.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fout, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, String> findLinksForDocumentNumbers(List<Integer> documentNumbers) {
        Map<Integer, String> result = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("index.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.replace(" ", "").split("-") ;
                Integer documentNumberCurrentLine = Integer.parseInt(values[0]);
                if (documentNumbers.contains(documentNumberCurrentLine)) {
                    result.put(documentNumberCurrentLine, values[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
