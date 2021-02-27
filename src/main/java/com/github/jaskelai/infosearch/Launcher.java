package com.github.jaskelai.infosearch;

public class Launcher {

    public static final int NUM_OF_SITES = 200;
    public static final String SITE = "https://www.sovsport.ru";

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.crawl();
    }
}
