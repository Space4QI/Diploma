package org.example.metrics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MetricsSimulator {

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 5000) // каждые 5 секунд
    public void simulateTraffic() {
        try {
            restTemplate.getForObject("http://localhost:8080/api/events", String.class);
            restTemplate.getForObject("http://localhost:8080/api/teams", String.class);
        } catch (Exception e) {
        }
    }
}
