package com.sangamesh.llm.error;

public class LlmUnavailableException extends LlmProviderException {

    public LlmUnavailableException(String provider, String message) {
        super(provider, message);
    }

    public LlmUnavailableException(String provider, String message, Throwable cause) {
        super(provider, message, cause);
    }
}
