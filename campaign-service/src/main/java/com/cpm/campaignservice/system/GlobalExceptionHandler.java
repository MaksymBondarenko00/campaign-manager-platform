package com.cpm.campaignservice.system;

import com.cpm.campaignservice.system.exceptions.CampaignValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampaignValidationException.class)
    public ResponseEntity<String> handleCampaignValidation(CampaignValidationException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());

    }

}
