package com.github.jaskelai.infosearch;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Tokenizer {

    public void tokenize(boolean writeIntoSingleFile) {
        File directory = new File("sites");
        File[] files = directory.listFiles();
        if (files == null) return;
        int i = 1;
        for (File file : files) {
            if (writeIntoSingleFile) {
                tokenizeAndWriteFile(file, true, null);
            } else {
                tokenizeAndWriteFile(file, false, i);
                i++;
            }
        }
    }

    private void tokenizeAndWriteFile(File source, boolean needToWriteInSingleFile, Integer index) {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\p{P}", " ");
                tokens = new LinkedList<>(Arrays.asList(line.split("(?=\\p{javaSpaceChar}|(\\p{Lu}\\p{javaLowerCase}))")));
                tokens = tokens.stream()
                        .map(token -> token.replace(" ", ""))
                        .filter(token -> !token.equals(""))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (needToWriteInSingleFile) {
            Utils.writeToFileLineByLine("words/words.txt", tokens);
        } else {
            Utils.writeToFileLineByLine("words-token/" + index + ".txt", tokens);
        }
    }
}
