package com.coding.test.webpageanalyser.controller;

import com.coding.test.webpageanalyser.model.Report;
import com.coding.test.webpageanalyser.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    public void givenWebpageUrl_whenProcessWebPageIsCalled_thenReturnOkStatus() throws Exception {
        when(reportService.processWebpage()).thenReturn(new Report(emptyMap(), emptyMap()));

        this.mockMvc.perform(get("/webpage-analyser/report"))
                .andExpect(status().isOk());

        verify(reportService).processWebpage();
    }

    @Test
    public void givenInCorrectWebpageUrl_whenProcessWebPageIsCalled_thenReturnInternalServerError() throws Exception {
        when(reportService.processWebpage()).thenThrow(IOException.class);

        this.mockMvc.perform(get("/webpage-analyser/report"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Something went wrong!")));

        verify(reportService).processWebpage();
    }
}
