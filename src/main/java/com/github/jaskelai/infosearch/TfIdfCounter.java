package com.github.jaskelai.infosearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TfIdfCounter {

    public void countTf() {
        // слово к мапе номер документа к количеству вхождений
        Map<String, Map<Integer, Integer>> tokensTf = new HashMap<>();
        File directory = new File("words-lemma");
        File[] files = directory.listFiles();
        if (files == null) return;
        try (BufferedReader br = new BufferedReader(new FileReader("words/words_lemmatized.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (tokensTf.containsKey(line)) continue;
                int i = 1;
                for (File file : files) {
                    try (BufferedReader _br = new BufferedReader(new FileReader(file))) {
                        String _line;
                        while ((_line = _br.readLine()) != null) {
                            if (line.equals(_line)) {
                                if (tokensTf.containsKey(line)) {
                                    if (tokensTf.get(line).containsKey(i)) {
                                        Map<Integer, Integer> numToNumOfEntries = tokensTf.get(line);
                                        Integer newNumOfEntries = numToNumOfEntries.get(i) + 1;
                                        numToNumOfEntries.put(i, newNumOfEntries);
                                    } else {
                                        tokensTf.get(line).put(i, 1);
                                    }
                                } else {
                                    Map<Integer, Integer> indexes = new HashMap<>();
                                    indexes.put(i, 1);
                                    tokensTf.put(line, indexes);
                                }
                            }
                        }
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Map<String, Map<Integer, Integer>> filteredTf = tokensTf.entrySet().stream()
                    .filter(entry -> !entry.getValue().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            // записываем tf
            List<String> resultTf = filteredTf.entrySet().stream()
                    .map(entry -> "" + entry.getKey() + " " + entry.getValue().toString())
                    .collect(Collectors.toList());
            Utils.writeToFileLineByLine("words/words_tf.txt", resultTf);
            // записываем idf
            Map<String, Double> filteredIdf = filteredTf.entrySet().stream()
                    .map(entry -> Map.entry(entry.getKey(), Math.log((double) files.length / entry.getValue().size())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            List<String> resultIdf = filteredIdf.entrySet().stream()
                    .map(entry -> "" + entry.getKey() + " " + entry.getValue())
                    .collect(Collectors.toList());
            Utils.writeToFileLineByLine("words/words_idf.txt", resultIdf);
            // записываем tf-idf
            Map<String, Map<Integer, Double>> _resultTfIdf = new HashMap<>();
            for (Map.Entry<String, Map<Integer, Integer>> docsForWord : filteredTf.entrySet()) {
                Map<Integer, Double> tfIdfTemp = docsForWord.getValue().entrySet().stream()
                        .map(p -> Map.entry(p.getKey(), p.getValue() * filteredIdf.get(docsForWord.getKey())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                _resultTfIdf.put(docsForWord.getKey(), tfIdfTemp);
            }
            List<String> resultTfIdf = _resultTfIdf.entrySet().stream()
                    .map(entry -> "" + entry.getKey() + " " + entry.getValue())
                    .collect(Collectors.toList());
            Utils.writeToFileLineByLine("words/words_tf_idf.txt", resultTfIdf);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
