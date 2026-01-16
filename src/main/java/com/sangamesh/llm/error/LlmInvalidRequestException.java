package com.sangamesh.llm.error;

public class LlmInvalidRequestException extends LlmProviderException {

    public LlmInvalidRequestException(String provider, String message) {
        super(provider, message);
    }

    public LlmInvalidRequestException(String provider, String message, Throwable cause) {
        super(provider, message, cause);
    }
}
