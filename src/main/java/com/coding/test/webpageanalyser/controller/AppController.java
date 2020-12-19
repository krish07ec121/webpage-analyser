package com.coding.test.webpageanalyser.controller;

import com.coding.test.webpageanalyser.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    private AppService appService;

    @RequestMapping("/webpage-analyser/report")
    public ResponseEntity processWebPage() {
        try {
            return ResponseEntity.ok(appService.processWebpage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

}
