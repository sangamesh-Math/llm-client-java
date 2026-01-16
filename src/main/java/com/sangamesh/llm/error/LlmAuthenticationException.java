package com.sangamesh.llm.error;

public class LlmAuthenticationException extends LlmProviderException {

    public LlmAuthenticationException(String provider, String message) {
        super(provider, message);
    }

    public LlmAuthenticationException(String provider, String message, Throwable cause) {
        super(provider, message, cause);
    }
}
