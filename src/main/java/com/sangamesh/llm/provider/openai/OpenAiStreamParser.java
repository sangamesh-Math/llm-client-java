package com.sangamesh.llm.provider.openai;

final class OpenAiStreamParser {

    private OpenAiStreamParser() {
    }

    static String extractDelta(String json) {
        // Looks for: "content":"..."
        int index = json.indexOf("\"content\"");
        if (index == -1) {
            return "";
        }

        int start = json.indexOf('"', index + 10) + 1;
        int end = json.indexOf('"', start);
        return json.substring(start, end);
    }
}

