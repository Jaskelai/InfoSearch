package com.github.jaskelai.infosearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InvertedIndexer {

    public void execute() {
        Map<String, Set<Integer>> tokens = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("words/words_lemmatized.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                File directory = new File("words-lemma");
                File[] files = directory.listFiles();
                if (files == null) return;
                int i = 1;
                for (File file : files) {
                    try (BufferedReader _br = new BufferedReader(new FileReader(file))) {
                        String _line;
                        while ((_line = _br.readLine()) != null) {
                            if (line.equals(_line)) {
                                if (tokens.containsKey(line)) {
                                    tokens.get(line).add(i);
                                } else {
                                    Set<Integer> indexes = new HashSet<>();
                                    indexes.add(i);
                                    tokens.put(line, indexes);
                                }
                            }
                        }
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            List<String> result = tokens.entrySet().stream()
                    .map(entry -> "" + entry.getKey() + " " + entry.getValue().toString())
                    .collect(Collectors.toList());
            Utils.writeToFileLineByLine("words/words_indexed.txt", result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
