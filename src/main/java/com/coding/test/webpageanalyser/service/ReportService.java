package com.coding.test.webpageanalyser.service;

import com.coding.test.webpageanalyser.components.WebpageScanner;
import com.coding.test.webpageanalyser.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static final String WHITESPACE = " ";

    @Value("${webpage.anlyser.record.limit:10}")
    private int maxLimit;

    @Autowired
    private WebpageScanner webpageScanner;

    /*
    *  After getting all contents / texts from WebpageScanner, put word and word-pair in different map
    *  if word or word-pair already exists increment the counter in the map
    *  It will also sort the both map based on word's / word-pair's count.
    * */
    public Report processWebpage() throws IOException {
        List<String> texts = webpageScanner.getDataFromHtmlDocument();
        Map<String, Integer> wordMap = new HashMap<>();
        Map<String, Integer> wordPairMap = new HashMap<>();

        texts.forEach(text -> {
            String[] words = text.split(WHITESPACE);

            for (int i = 0; i < words.length; i++) {
                updateMap(words[i].trim(), wordMap);

                if (i < words.length - 1) {
                    String wordPair = words[i].trim() + WHITESPACE + words[i + 1].trim();
                    updateMap(wordPair, wordPairMap);
                }
            }
        });

        return new Report(sortMap(wordMap), sortMap(wordPairMap));
    }

    private void updateMap(String word, Map<String, Integer> map) {
        Integer count = map.getOrDefault(word, 0);
        count += 1;
        map.put(word, count);
    }

    private Map<String, Integer> sortMap(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(maxLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
