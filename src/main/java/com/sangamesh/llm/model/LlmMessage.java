package com.sangamesh.llm.model;

import java.util.Objects;

public final class LlmMessage {

    public enum Role {
        SYSTEM,
        USER,
        ASSISTANT
    }

    private final Role role;
    private final String content;

    private LlmMessage(Role role, String content) {
        this.role = Objects.requireNonNull(role, "role must not be null");
        this.content = Objects.requireNonNull(content, "content must not be null");
    }

    public Role role() {
        return role;
    }

    public String content() {
        return content;
    }

    public static LlmMessage system(String text) {
        return new LlmMessage(Role.SYSTEM, text);
    }

    public static LlmMessage user(String text) {
        return new LlmMessage(Role.USER, text);
    }

    public static LlmMessage assistant(String text) {
        return new LlmMessage(Role.ASSISTANT, text);
    }
}

