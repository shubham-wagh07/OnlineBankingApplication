package com.shubham.bankingapp.service;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MockarooService {

    private final WebClient webClient;

    public MockarooService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.mockaroo.com")
                                        .defaultHeader("X-Api-Key", "af1e9ba0") // Replace with your Mockaroo API key
                                        .build();
    }

    public String getMockData() {
        return webClient.get()
                        .uri("/api/af1e9ba0?count=1") // Replace with your Mockaroo endpoint
                        .retrieve()
                        .bodyToMono(String.class)
                        .block(); // Using block() for synchronous behavior
    }
}
