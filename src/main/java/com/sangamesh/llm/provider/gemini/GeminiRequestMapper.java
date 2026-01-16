package com.sangamesh.llm.provider.gemini;

import com.sangamesh.llm.model.LlmMessage;
import com.sangamesh.llm.model.LlmRequest;

import java.util.stream.Collectors;

final class GeminiRequestMapper {

    private GeminiRequestMapper() {}

    static String toJson(LlmRequest request) {
        String text = request.messages().stream()
                .map(LlmMessage::content)
                .collect(Collectors.joining("\n"));

        return """
        {
          "contents": [{
            "parts": [{
              "text": "%s"
            }]
          }]
        }
        """.formatted(escape(text));
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }
}
