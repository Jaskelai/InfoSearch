package com.github.jaskelai.infosearch;

public class Launcher {

    public static final int NUM_OF_SITES = 200;
    public static final String SITE = "https://www.sovsport.ru";

    public static void main(String[] args) {
       // Задание 1
//        Crawler crawler = new Crawler();
//        crawler.crawl();
        // Задание 2
//        Tokenizer tokenizer = new Tokenizer();
//        tokenizer.tokenize(true);
//        Lemmatizer lemmatizer = new Lemmatizer();
//        lemmatizer.lemmatizeSingle();
        // Задание 3
//        Tokenizer tokenizer = new Tokenizer();
//        tokenizer.tokenize(false);
//        Lemmatizer lemmatizer = new Lemmatizer();
//        lemmatizer.lemmatizeMultiple();
//        InvertedIndexer indexer = new InvertedIndexer();
//        indexer.execute();
        BooleanSearch booleanSearch = new BooleanSearch();
        booleanSearch.execute();
    }
}
