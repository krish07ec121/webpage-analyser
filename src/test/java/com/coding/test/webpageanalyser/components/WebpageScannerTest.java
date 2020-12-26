package com.coding.test.webpageanalyser.components;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WebpageScannerTest {

    @InjectMocks
    private WebpageScanner webpageScanner;

    @Before
    public void setup() {
        Whitebox.setInternalState(webpageScanner, "websiteUrlToScan", "https://www.314e.com/");
    }

    @Test
    public void givenUrl_whenGetDataFromHtmlDocumentIsCalled_thenReturnListOfTexts() throws Exception {
        List<String> texts = webpageScanner.getDataFromHtmlDocument();
        Assertions.assertThat(texts).isNotEmpty();
    }

    @Test(expected = IOException.class)
    public void givenInvalidUrl_whenGetDataFromHtmlDocumentIsCalled_thenReturnIOException() throws Exception {
        Whitebox.setInternalState(webpageScanner, "websiteUrlToScan", "https://dfsdfsdfsf");
        List<String> texts = webpageScanner.getDataFromHtmlDocument();
    }
}
