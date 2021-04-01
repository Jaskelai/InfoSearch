package com.github.jaskelai.infosearch;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanVectorizedSearch {

    public Map<Integer, Double> execute(String text) {
        String fullQuery = text;
        String[] queries = fullQuery.split(" ");
        Map<Integer, Double> indexes = new HashMap<>();
        for (int i = 0; i < queries.length; i++) {
            if (i == 0) {
                Map<Integer, Double> indexesForWord = getIndexesForWord(queries[0]);
                indexes.putAll(indexesForWord);
                continue;
            }
            switch (queries[i]) {
                case "AND":
                    indexes = and(indexes, queries[i+1]);
                    break;
                case "OR":
                    indexes = or(indexes, queries[i+1]);
                    break;
                case "NOT":
                    indexes = not(indexes, queries[i+1]);
            }
        }
        return indexes;
    }

    private Map<Integer, Double> getIndexesForWord(String word) {
        Map<Integer, Double> result = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("words/words_tf_idf.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.replace(" ", "").split("\\{") ;
                String wordCurrentLine = values[0];
                if (wordCurrentLine.equals(word)) {
                    Map<Integer, Double> indexes = Arrays.stream(values[1].replace("}", "")
                            .split(","))
                            .map(s -> {
                                String[] kV = s.split("=");
                                return Map.entry(Integer.parseInt(kV[0]), Double.parseDouble(kV[1]));
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    result.putAll(indexes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<Integer, Double> and(Map<Integer, Double> query1, String query2) {
        Map<Integer, Double> indexesForQuery2 = getIndexesForWord(query2);
        return query1.entrySet().stream()
                .distinct()
                .filter(entry -> indexesForQuery2.containsKey(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() + indexesForQuery2.get(e.getKey())));
    }

    private Map<Integer, Double> or(Map<Integer, Double> query1, String query2) {
        Map<Integer, Double> indexesForQuery2 = getIndexesForWord(query2);
        indexesForQuery2.putAll(query1);
        return new HashMap<>(indexesForQuery2);
    }

    private Map<Integer, Double> not(Map<Integer, Double> query1, String query2) {
        Map<Integer, Double> indexesForQuery2 = getIndexesForWord(query2);
        return query1.entrySet().stream()
                .distinct()
                .filter(x -> !indexesForQuery2.containsKey(x))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
