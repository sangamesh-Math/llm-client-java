package com.sangamesh.llm.provider.gemini;

import java.time.Duration;
import java.util.Objects;

public final class GeminiConfig {

    private final String apiKey;
    private final String baseUrl;
    private final Duration timeout;

    private GeminiConfig(Builder builder) {
        this.apiKey = Objects.requireNonNull(builder.apiKey, "apiKey must not be null");
        this.baseUrl = builder.baseUrl != null
                ? builder.baseUrl
                : "https://generativelanguage.googleapis.com/v1beta";
        this.timeout = builder.timeout != null
                ? builder.timeout
                : Duration.ofSeconds(30);
    }

    public String apiKey() {
        return apiKey;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public Duration timeout() {
        return timeout;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String apiKey;
        private String baseUrl;
        private Duration timeout;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public GeminiConfig build() {
            return new GeminiConfig(this);
        }
    }
}
