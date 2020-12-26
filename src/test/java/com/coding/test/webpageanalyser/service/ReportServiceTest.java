package com.coding.test.webpageanalyser.service;

import com.coding.test.webpageanalyser.components.WebpageScanner;
import com.coding.test.webpageanalyser.model.Report;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private WebpageScanner webpageScanner;

    @Before
    public void setup() {
        Whitebox.setInternalState(reportService, "maxLimit", 10);
    }

    @Test
    public void givenDataFromWebPage_whenProcessWebpageIsCalled_thenReturnReport() throws Exception {
        List<String> texts = Arrays.asList("Students studied The Importance of Being Earnest and wrote essays focusing on the main characters. Click here to navigate to the link",
                "Following in class discussions about memories, nostalgia and descriptive writing, students wrote about The Snow of 2010.",
                "Having studied war poetry students responded imaginatively in writing to the texts they had explored. Click here to navigate to the link",
                "Having studied war poetry students responded imaginatively in writing to the texts they had explored. Click here to navigate to the link",
                "Students write a newspaper article based on events in the novel 'Trash'. Click here to navigate to the link");

        when(webpageScanner.getDataFromHtmlDocument()).thenReturn(texts);

        Report report = reportService.processWebpage();
        System.out.println(report);

        Map<String, Integer> expectedFrequentWordsMap = getExpectedFrequentWordsMap();
        Map<String, Integer> expectedFrequentWordPairsMap = getExpectedFrequentWordPairsMap();


        Assertions.assertThat(report).isNotNull();
        Assertions.assertThat(report.getFrequentWords()).isEqualTo(expectedFrequentWordsMap);
        Assertions.assertThat(report.getFrequentWordPairs()).isEqualTo(expectedFrequentWordPairsMap);

        verify(webpageScanner).getDataFromHtmlDocument();
    }


    private Map<String, Integer> getExpectedFrequentWordsMap() {
        Map<String, Integer> expectedFrequentWordsMap = new HashMap<>();
        expectedFrequentWordsMap.put("to", 10);
        expectedFrequentWordsMap.put("the", 8);
        expectedFrequentWordsMap.put("Click", 4);
        expectedFrequentWordsMap.put("navigate", 4);
        expectedFrequentWordsMap.put("in", 4);
        expectedFrequentWordsMap.put("here", 4);
        expectedFrequentWordsMap.put("link", 4);
        expectedFrequentWordsMap.put("students", 3);
        expectedFrequentWordsMap.put("studied", 3);
        expectedFrequentWordsMap.put("about", 2);

        return expectedFrequentWordsMap;
    }

    private Map<String, Integer> getExpectedFrequentWordPairsMap() {
        Map<String, Integer> expectedFrequentWordsMap = new HashMap<>();
        expectedFrequentWordsMap.put("to the", 6);
        expectedFrequentWordsMap.put("the link", 4);
        expectedFrequentWordsMap.put("Click here", 4);
        expectedFrequentWordsMap.put("to navigate", 4);
        expectedFrequentWordsMap.put("navigate to", 4);
        expectedFrequentWordsMap.put("here to", 4);
        expectedFrequentWordsMap.put("they had", 2);
        expectedFrequentWordsMap.put("the texts", 2);
        expectedFrequentWordsMap.put("writing to", 2);
        expectedFrequentWordsMap.put("students responded", 2);

        return expectedFrequentWordsMap;
    }
}
