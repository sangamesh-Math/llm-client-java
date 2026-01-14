package com.sangamesh.llm.provider.openai;

final class OpenAiResponseParser {

    private OpenAiResponseParser() {
    }

    static String extractText(String responseBody) {
        // Minimal parsing (safe for now, replace later)
        // Looks for: "content":"..."
        int index = responseBody.indexOf("\"content\"");
        if (index == -1) {
            return "";
        }

        int start = responseBody.indexOf('"', index + 10) + 1;
        int end = responseBody.indexOf('"', start);
        return responseBody.substring(start, end);
    }
}

