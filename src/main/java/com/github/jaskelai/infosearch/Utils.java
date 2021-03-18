package com.github.jaskelai.infosearch;

import java.io.*;
import java.util.List;

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
}
