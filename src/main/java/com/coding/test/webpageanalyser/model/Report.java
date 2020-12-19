package com.coding.test.webpageanalyser.model;

import java.util.Map;

public class Report {

    private Map<String, Integer> frequentWords;
    private Map<String, Integer> frequentWordPairs;

    public Report(Map<String, Integer> frequentWords, Map<String, Integer> frequentWordPairs) {
        this.frequentWords = frequentWords;
        this.frequentWordPairs = frequentWordPairs;
    }

    public Map<String, Integer> getFrequentWords() {
        return frequentWords;
    }

    public void setFrequentWords(Map<String, Integer> frequentWords) {
        this.frequentWords = frequentWords;
    }

    public Map<String, Integer> getFrequentWordPairs() {
        return frequentWordPairs;
    }

    public void setFrequentWordPairs(Map<String, Integer> frequentWordPairs) {
        this.frequentWordPairs = frequentWordPairs;
    }
}
