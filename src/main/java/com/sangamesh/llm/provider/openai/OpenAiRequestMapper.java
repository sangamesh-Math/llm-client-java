package com.sangamesh.llm.provider.openai;

import com.sangamesh.llm.model.LlmMessage;
import com.sangamesh.llm.model.LlmRequest;

import java.util.stream.Collectors;

final class OpenAiRequestMapper {

    private OpenAiRequestMapper() {
    }

    static String toJson(LlmRequest request) {
        return toJson(request, false);
    }

    static String toJson(LlmRequest request, boolean stream) {
        String messagesJson = request.messages().stream()
                .map(OpenAiRequestMapper::messageToJson)
                .collect(Collectors.joining(","));

        return """
            {
              "model": "%s",
              "stream": %s,
              "messages": [%s]
            }
            """.formatted(request.model(), stream, messagesJson);
    }



    private static String messageToJson(LlmMessage message) {
        return """
                {
                  "role": "%s",
                  "content": "%s"
                }
                """.formatted(
                message.role().name().toLowerCase(),
                escape(message.content())
        );
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }
}
