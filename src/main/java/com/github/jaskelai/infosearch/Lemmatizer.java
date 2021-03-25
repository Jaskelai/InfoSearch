package com.github.jaskelai.infosearch;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.WrongCharaterException;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lemmatizer {

    public void lemmatize() {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("words/words.txt"))) {
            LuceneMorphology russianLuceneMorphology = new RussianLuceneMorphology();
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    List<String> wordBaseForms = russianLuceneMorphology.getNormalForms(line);
                    tokens.addAll(wordBaseForms);
                } catch (WrongCharaterException e) {

                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
            Utils.writeToFileLineByLine("words/words_lemmatized.txt", tokens);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lemmatizeMultiple() {
        File directory = new File("words-token");
        File[] files = directory.listFiles();
        if (files == null) return;
        int i = 1;
        for (File file : files) {
            List<String> tokens = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                LuceneMorphology russianLuceneMorphology = new RussianLuceneMorphology();
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        List<String> wordBaseForms = russianLuceneMorphology.getNormalForms(line);
                        tokens.addAll(wordBaseForms);
                    } catch (WrongCharaterException e) {

                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
                Utils.writeToFileLineByLine("words-lemma/" + i + ".txt", tokens);
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
