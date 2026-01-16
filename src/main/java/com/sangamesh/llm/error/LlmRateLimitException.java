package com.sangamesh.llm.error;

public class LlmRateLimitException extends LlmProviderException {

    public LlmRateLimitException(String provider, String message) {
        super(provider, message);
    }

    public LlmRateLimitException(String provider, String message, Throwable cause) {
        super(provider, message, cause);
    }
}

