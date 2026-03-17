package com.cpm.campaignservice.click;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clicks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClickController {

    private final ClickService service;

    @PostMapping("/{campaignId}")
    public ResponseEntity<Void> registerClick(@PathVariable("campaignId") Long campaignId) {
        service.registerClick(campaignId);

        return ResponseEntity.ok().build();
    }
}
