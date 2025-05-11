package com.bajaj.bfhl;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppStartupRunner {

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        RestTemplate restTemplate = new RestTemplate();

        String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "Mahak Dhanwani");
        requestBody.put("regNo", "REG0827CI2212085");
        requestBody.put("email", "mahakdhanwani27@gmail.com");

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(generateUrl, requestBody, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String webhook = (String) response.getBody().get("webhook");
                String token = (String) response.getBody().get("accessToken");

                System.out.println("Webhook: " + webhook);
                System.out.println("Token: " + token);

                String finalQuery = "SELECT MAX(p.AMOUNT) AS SALARY, e.FIRST_NAME || ' ' || e.LAST_NAME AS NAME, "
                        + "(CAST(strftime('%Y','now') AS INT) - CAST(strftime('%Y', e.DOB) AS INT)) AS AGE, "
                        + "d.DEPARTMENT_NAME FROM PAYMENTS p "
                        + "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID "
                        + "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID "
                        + "WHERE strftime('%d', p.PAYMENT_TIME) != '01' "
                        + "ORDER BY p.AMOUNT DESC LIMIT 1";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(token);

                Map<String, String> queryPayload = new HashMap<>();
                queryPayload.put("finalQuery", finalQuery);

                HttpEntity<Map<String, String>> sqlRequest = new HttpEntity<>(queryPayload, headers);
                ResponseEntity<String> sqlResponse = restTemplate.postForEntity(webhook, sqlRequest, String.class);

                System.out.println("Submission response: " + sqlResponse.getBody());
            } else {
                System.out.println("Webhook generation failed. Status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException e) {
            System.out.println("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}