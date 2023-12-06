package com.coderscampus.AssignmentSubmissionApp.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.dto.GhlSlackMessageCustomData;
import com.coderscampus.AssignmentSubmissionApp.dto.GhlWebhookRequest;

@Service
public class NotificationService {
    private static final String BROADCAST_MESSAGE = "This is a broadcast message to all Coders Campus Code Reviewers.";
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("#{'${emails.codeReviewers}'.split(',')}")
    private List<String> codeReviewers;

    @Autowired
    private Environment environment;
    @Value("${domainRoot}")
    private String domainRoot;

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public ResponseEntity<?> sendEmail(String toEmail, String message, String textMessage, String subject) throws JSONException {
        if (toEmail == null || message == null)
            return null;
        if (message != null) {
            message = message.replace("\n", "<br/>");
        }

        RestTemplate rt = new RestTemplate();
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("from", "Trevor Page <trevor@coderscampus.com>");
        data.add("subject", subject);
        data.add("to", toEmail);
        if (!message.contains(BROADCAST_MESSAGE)) {
            data.add("cc", "trevor@coderscampus.com");
        }
        data.add("html", message);
        data.add("text", textMessage);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(data, createHeaders(username, password));

        var activeProfiles = this.environment != null ? this.environment.getActiveProfiles() : new String[]{"local"};
        if (activeProfiles != null && Arrays.stream(activeProfiles).anyMatch(profile -> "local".equalsIgnoreCase(profile))) {
            log.warn("Local profile detected, suppressing the sending of email.");
            return ResponseEntity.ok().build();
        } else {
            ResponseEntity<String> response = rt.exchange("https://api.mailgun.net/v3/coderscampus.com/messages", HttpMethod.POST,
                    entity, String.class);
            log.info("Email sent to {}", toEmail);
            return response;
        }


    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    public void sendAssignmentStatusUpdateStudent(String oldStatus, Assignment assignment) {

        String emailBody = "Hello " + assignment.getUser().getName() + ", " + "\n\n Your assignment "
                + assignment.getNumber() + " has gone from [" + oldStatus + "] to [" + assignment.getStatus() + "]";
        String htmlEmailBody = emailBody;
        String link = assignment.getCodeReviewVideoUrl();
        if (StringUtils.hasText(link)) {
            htmlEmailBody = htmlEmailBody + "\n\nHere's your code review video link: <a href='" + link + "'>" + link + "</a>";
        }

        try {

            sendEmail(assignment.getUser().getUsername(), htmlEmailBody, emailBody, "Assignment Status Update");
        } catch (JSONException e) {
            log.error("Error sending email", e);
        }
    }

    public void sendAssignmentStatusUpdateCodeReviewer(String oldStatus, Assignment assignment) {
        String message = "Hello, " + assignment.getUser().getName() + "'s assignment "
                + assignment.getNumber() + " has gone from [" + oldStatus + "] to [" + assignment.getStatus() + "]." +
                "\n\n<" + domainRoot + "/dashboard|Click Here to Visit Assignment Submission Dashboard>";

        sendSlackMessage(message, "CODE_REVIEWERS");
    }
    
    public void sendCongratsOnAssignmentSubmissionSlackMessage(Assignment assignment, String channelId) {
        sendSlackMessage("Congrats to " + assignment.getUser().getName() + " for submitting assignment #" + assignment.getNumber() + ". " + HypeUpService.getHypeUpMessage(), channelId);
    }

    public void sendSlackMessage(String message, String channelId) {
        RestTemplate rt = new RestTemplate();

        GhlWebhookRequest request = new GhlWebhookRequest();

        GhlSlackMessageCustomData customData = new GhlSlackMessageCustomData();
        customData.setChannel(channelId);
        customData.setMessage(message);
        request.setCustomData(customData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GhlWebhookRequest> entity = new HttpEntity<>(request, headers);

        rt.exchange("https://courses.coderscampus.com/ghl/slack-message", HttpMethod.POST, entity, String.class);
    }

}
