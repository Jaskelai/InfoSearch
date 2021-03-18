package com.github.jaskelai.infosearch;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenizer {

    public void tokenize() {
        File directory = new File("sites");
        File[] files = directory.listFiles();
        if (files == null) return;
        for (File file : files) {
            tokenizeAndWriteFile(file);
        }
    }

    private void tokenizeAndWriteFile(File source) {
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
        Utils.writeToFileLineByLine("words/words.txt", tokens);
    }
}
