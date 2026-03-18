package com.cpm.campaignservice.click;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clicks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Clicks", description = "API for registering advertisement clicks")
public class ClickController {

    ClickService service;

    @Operation(
            summary = "Register click",
            description = "Registers a click for the specified campaign and deducts the bid amount from the campaign budget"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Click registered successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found"),
            @ApiResponse(responseCode = "400", description = "Campaign is inactive or has insufficient budget")
    })
    @PostMapping("/{campaignId}")
    public ResponseEntity<Void> registerClick(
            @Parameter(description = "Campaign ID for which the click is registered")
            @PathVariable("campaignId") Long campaignId
    ) {
        service.registerClick(campaignId);

        return ResponseEntity.ok().build();
    }
}