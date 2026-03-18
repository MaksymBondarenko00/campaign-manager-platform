package com.cpm.campaignservice.system.exceptions;

public class InvalidCampaignException extends RuntimeException {
    public InvalidCampaignException(String message) {
        super(message);
    }
}
