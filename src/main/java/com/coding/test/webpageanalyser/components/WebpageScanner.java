package com.coding.test.webpageanalyser.components;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WebpageScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebpageScanner.class);
    private static final int MAX_LEVEL_TO_EXPAND = 4;

    @Value("${webpage.anlyser.website.url}")
    private String websiteUrlToScan;

    private int currentLevel = 0;

    /*
    *  Get all contents / texts from Html document including hyperlinks
    *
    * */
    public List<String> getDataFromHtmlDocument() throws IOException {
        List<Document> consolidatedHyperLinkDocuments = new ArrayList<>();
        Set<String> visitedLinks = new HashSet<>();
        List<String> allTexts = new ArrayList<>();

        LOGGER.info("connecting to {}", websiteUrlToScan);
        Document rootPageDocument = Jsoup.connect(websiteUrlToScan).get();
        consolidatedHyperLinkDocuments.add(rootPageDocument);
        visitedLinks.add(websiteUrlToScan);
        currentLevel++;

        prepareConsolidatedHyperLinkDocuments(consolidatedHyperLinkDocuments, rootPageDocument, visitedLinks);

        consolidatedHyperLinkDocuments.forEach(document -> allTexts.addAll(document.getAllElements().eachText()));

        return allTexts;
    }


    /*
    *  Recursive function to fetch documents from hyperlinks up to level 4.
    *  It will prepare Consolidated Html document list from which we will to extract words.
    * */
    private void prepareConsolidatedHyperLinkDocuments(List<Document> consolidatedHyperLinkDocuments, Document rootPageDocument, Set<String> visitedLinks) {
        currentLevel++;
        if (currentLevel > MAX_LEVEL_TO_EXPAND) {
            LOGGER.info("Reached to maximum level, No further processing..");
            return;
        }

        Set<String> urlLinks = rootPageDocument.getElementsByTag("a")
                .stream().filter(element -> element.attributes().get("href").startsWith(websiteUrlToScan))
                .map(element -> element.attributes().get("href"))
                .filter(url -> !visitedLinks.contains(url))
                .collect(Collectors.toSet());


        visitedLinks.addAll(urlLinks);
        for (String url : urlLinks) {
            LOGGER.info("connecting to {}", url);
            Document doc;
            try {
                doc = Jsoup.connect(url).get();
            } catch (Exception e) {
                LOGGER.error("Error connecting url: {}, errorMessage: {}", url, e.getMessage());
                return;
            }

            consolidatedHyperLinkDocuments.add(doc);

            prepareConsolidatedHyperLinkDocuments(consolidatedHyperLinkDocuments, doc, visitedLinks);
        }
        currentLevel--;
    }
}
