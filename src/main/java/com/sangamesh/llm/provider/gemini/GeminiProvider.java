package com.sangamesh.llm.provider.gemini;

import com.sangamesh.llm.error.LlmProviderException;
import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;
import com.sangamesh.llm.provider.LlmProvider;
import com.sangamesh.llm.stream.LlmStream;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class GeminiProvider implements LlmProvider {

    private static final String PROVIDER_NAME = "gemini";

    private final GeminiConfig config;
    private final HttpClient httpClient;

    public GeminiProvider(GeminiConfig config) {
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
        return model != null && model.startsWith("gemini");
    }

    @Override
    public LlmResponse generate(LlmRequest request) {
        try {
            String body = GeminiRequestMapper.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(
                            config.baseUrl()
                                    + "/models/"
                                    + request.model()
                                    + ":generateContent?key="
                                    + config.apiKey()
                    ))
                    .header("Content-Type", "application/json")
                    .timeout(config.timeout())
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new LlmProviderException(
                        PROVIDER_NAME,
                        "Gemini error: " + response.body()
                );
            }

            String text = GeminiResponseParser.extractText(response.body());

            return new LlmResponse(
                    text,
                    null,
                    PROVIDER_NAME,
                    request.model()
            );

        } catch (Exception e) {
            throw new LlmProviderException(PROVIDER_NAME, "Gemini request failed", e);
        }
    }

    @Override
    public LlmStream stream(LlmRequest request) {
        throw new UnsupportedOperationException("Gemini streaming not implemented yet");
    }
}
