package com.sangamesh.llm.provider.gemini;

final class GeminiResponseParser {

    private GeminiResponseParser() {}

    static String extractText(String body) {
        int index = body.indexOf("\"text\"");
        if (index == -1) return "";

        int start = body.indexOf('"', index + 6) + 1;
        int end = body.indexOf('"', start);
        return body.substring(start, end);
    }
}
