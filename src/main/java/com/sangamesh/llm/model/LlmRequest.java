package com.sangamesh.llm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class LlmRequest {

    private final String model;
    private final List<LlmMessage> messages;
    private final Map<String, Object> parameters;

    private LlmRequest(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model must not be null");
        this.messages = Collections.unmodifiableList(new ArrayList<>(builder.messages));
        this.parameters = Collections.unmodifiableMap(new HashMap<>(builder.parameters));
    }

    public String model() {
        return model;
    }

    public List<LlmMessage> messages() {
        return messages;
    }

    public Map<String, Object> parameters() {
        return parameters;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String model;
        private final List<LlmMessage> messages = new ArrayList<>();
        private final Map<String, Object> parameters = new HashMap<>();

        private Builder() {
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder addMessage(LlmMessage message) {
            this.messages.add(Objects.requireNonNull(message, "message must not be null"));
            return this;
        }

        public Builder parameter(String key, Object value) {
            this.parameters.put(
                    Objects.requireNonNull(key, "key must not be null"),
                    Objects.requireNonNull(value, "value must not be null")
            );
            return this;
        }

        public LlmRequest build() {
            return new LlmRequest(this);
        }
    }
}
