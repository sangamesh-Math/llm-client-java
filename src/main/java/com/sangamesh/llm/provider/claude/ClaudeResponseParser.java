package com.sangamesh.llm.provider.claude;

final class ClaudeResponseParser {

    private ClaudeResponseParser() {}

    static String extractText(String json) {
        int idx = json.indexOf("\"text\"");
        if (idx == -1) return "";

        int start = json.indexOf('"', idx + 6) + 1;
        int end = json.indexOf('"', start);
        return json.substring(start, end);
    }
}
