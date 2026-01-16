package com.sangamesh.llm.provider.claude;

import com.sangamesh.llm.error.*;
import com.sangamesh.llm.model.LlmMessage;
import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;
import com.sangamesh.llm.provider.LlmProvider;
import com.sangamesh.llm.stream.LlmStream;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

public final class ClaudeProvider implements LlmProvider {

    private static final String PROVIDER_NAME = "claude";

    private final ClaudeConfig config;
    private final HttpClient httpClient;

    public ClaudeProvider(ClaudeConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(config.timeout())
                .build();
    }

    @Override
    public String name() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean supports(String model) {
        return model != null && model.startsWith("claude");
    }

    @Override
    public LlmResponse generate(LlmRequest request) {
        try {
            String prompt = request.messages().stream()
                    .map(LlmMessage::content)
                    .collect(Collectors.joining("\n"));

            String body = """
                {
                  "model": "%s",
                  "max_tokens": 512,
                  "messages": [
                    { "role": "user", "content": "%s" }
                  ]
                }
                """.formatted(request.model(), escape(prompt));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(config.baseUrl() + "/messages"))
                    .header("x-api-key", config.apiKey())
                    .header("anthropic-version", "2023-06-01")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 401) {
                throw new LlmAuthenticationException(PROVIDER_NAME, "Invalid Claude API key");
            }

            if (response.statusCode() == 429) {
                throw new LlmRateLimitException(PROVIDER_NAME, "Claude rate limit exceeded");
            }

            if (response.statusCode() >= 400) {
                throw new LlmProviderException(PROVIDER_NAME, response.body());
            }

            String text = ClaudeResponseParser.extractText(response.body());

            return new LlmResponse(text, null, PROVIDER_NAME, request.model());

        } catch (LlmException e) {
            throw e;
        } catch (Exception e) {
            throw new LlmProviderException(PROVIDER_NAME, "Claude request failed", e);
        }
    }

    @Override
    public LlmStream stream(LlmRequest request) {
        throw new UnsupportedOperationException("Claude streaming not implemented yet");
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }
}
