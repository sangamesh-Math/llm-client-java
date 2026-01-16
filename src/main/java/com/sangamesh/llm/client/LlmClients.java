package com.sangamesh.llm.client;

public final class LlmClients {

    private LlmClients() {}

    public static LlmClientBuilder builder() {
        return new LlmClientBuilder();
    }
}
