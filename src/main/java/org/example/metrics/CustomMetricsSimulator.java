package org.example.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomMetricsSimulator {

    private final Random random = new Random();

    public CustomMetricsSimulator(MeterRegistry meterRegistry) {
        meterRegistry.gauge("fake_users_online", random, r -> r.nextInt(100));
    }
}