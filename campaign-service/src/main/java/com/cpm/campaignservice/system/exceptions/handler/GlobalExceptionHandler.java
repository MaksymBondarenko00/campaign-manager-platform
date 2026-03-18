package com.cpm.campaignservice.system.exceptions.handler;

import com.cpm.campaignservice.system.dto.ErrorResponse;
import com.cpm.campaignservice.system.exceptions.CampaignValidationException;
import com.cpm.campaignservice.system.exceptions.InsufficientBalanceException;
import com.cpm.campaignservice.system.exceptions.InvalidCampaignException;
import com.cpm.campaignservice.system.exceptions.ProductOwnershipException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampaignValidationException.class)
    public ResponseEntity<String> handleCampaignValidation(CampaignValidationException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());

    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(
            InsufficientBalanceException ex
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProductOwnershipException.class)
    public ResponseEntity<ErrorResponse> handleProductOwnership(
            ProductOwnershipException ex
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.FORBIDDEN.value(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(InvalidCampaignException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCampaign(
            InvalidCampaignException ex
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex
    ) {
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        "Internal server error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now()
                )
        );
    }

}
