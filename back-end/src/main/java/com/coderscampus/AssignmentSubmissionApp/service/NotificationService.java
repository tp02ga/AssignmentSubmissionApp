package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("#{'${emails.codeReviewers}'.split(',')}")
    private List<String> codeReviewers;

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
        data.add("html", message);
        data.add("text", textMessage);

        HttpEntity<MultiValueMap> entity = new HttpEntity<>(data, createHeaders(username, password));

        ResponseEntity<String> response = rt.exchange("https://api.mailgun.net/v3/coderscampus.com/messages", HttpMethod.POST,
                entity, String.class);
        log.info("Email sent to {}", toEmail);
        return response;
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
        List<String> recipients = new ArrayList<>();
        if (assignment.getStatus().contentEquals("Resubmitted")) {
            recipients.add(assignment.getCodeReviewer().getUsername());
        } else {
            recipients.addAll(codeReviewers);
        }
        StringBuffer emailBody = new StringBuffer();
        if (recipients.size() > 1) {
            emailBody.append("This is a broadcast message to all Coders Campus Code Reviewers.\n\n");
        }
        emailBody.append("Hello, " + assignment.getUser().getName() + "'s assignment "
                + assignment.getNumber() + " has gone from [" + oldStatus + "] to [" + assignment.getStatus() + "].");
        String htmlEmailBody = emailBody + "\n\n<a href='" + domainRoot + "/dashboard'>Click Here to Visit Assignment Submission Dashboard</a>";

        recipients.forEach(recipient -> {
            try {
                sendEmail(recipient, htmlEmailBody, emailBody.toString(), "[For Code Reviewers] Assignment Status Update");
            } catch (JSONException e) {
                log.error("Couldn't send email notification to code reviewers", e);
            }
        });

    }

}
