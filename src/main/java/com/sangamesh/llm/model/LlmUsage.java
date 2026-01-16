package com.sangamesh.llm.model;

public final class LlmUsage {

    private final int promptTokens;
    private final int completionTokens;
    private final int totalTokens;

    public LlmUsage(int promptTokens, int completionTokens, int totalTokens) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }

    public int promptTokens() {
        return promptTokens;
    }

    public int completionTokens() {
        return completionTokens;
    }

    public int totalTokens() {
        return totalTokens;
    }
}
