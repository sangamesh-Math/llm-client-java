package com.sangamesh.llm.error;

public class LlmProviderException extends LlmException {

    private final String provider;

    public LlmProviderException(String provider, String message) {
        super(message);
        this.provider = provider;
    }

    public LlmProviderException(String provider, String message, Throwable cause) {
        super(message, cause);
        this.provider = provider;
    }

    public String provider() {
        return provider;
    }
}
