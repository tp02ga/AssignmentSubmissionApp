package com.coderscampus.AssignmentSubmissionApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GhlWebhookRequest {
    private GhlSlackMessageCustomData customData;

    public GhlSlackMessageCustomData getCustomData() {
        return customData;
    }

    public void setCustomData(GhlSlackMessageCustomData customData) {
        this.customData = customData;
    }

    
}
