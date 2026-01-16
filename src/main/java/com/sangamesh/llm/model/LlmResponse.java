package com.sangamesh.llm.model;

import java.util.Objects;

public final class LlmResponse {

    private final String text;
    private final LlmUsage usage;
    private final String provider;
    private final String model;

    public LlmResponse(String text, LlmUsage usage, String provider, String model) {
        this.text = Objects.requireNonNull(text, "text must not be null");
        this.usage = usage;
        this.provider = Objects.requireNonNull(provider, "provider must not be null");
        this.model = Objects.requireNonNull(model, "model must not be null");
    }

    public String text() {
        return text;
    }

    public LlmUsage usage() {
        return usage;
    }

    public String provider() {
        return provider;
    }

    public String model() {
        return model;
    }
}

