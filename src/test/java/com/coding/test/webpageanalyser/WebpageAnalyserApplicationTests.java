package com.coding.test.webpageanalyser;

import com.coding.test.webpageanalyser.controller.ReportController;
import com.coding.test.webpageanalyser.model.Report;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class WebpageAnalyserApplicationTests {

    @Autowired
    private ReportController reportController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(reportController).isNotNull();
    }

    @Test
    public void testGetReport() throws Exception {
        Report report = this.restTemplate.getForObject("http://localhost:" + port + "/webpage-analyser/report", Report.class);

        Assertions.assertThat(report).isNotNull();
        Assertions.assertThat(report.getFrequentWords()).isNotNull();
        Assertions.assertThat(report.getFrequentWordPairs()).isNotNull();
    }
}
