package com.sangamesh.llm.provider.openai;

import com.sangamesh.llm.error.LlmAuthenticationException;
import com.sangamesh.llm.error.LlmException;
import com.sangamesh.llm.error.LlmProviderException;
import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;
import com.sangamesh.llm.provider.LlmProvider;
import com.sangamesh.llm.stream.LlmStream;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public final class OpenAiProvider implements LlmProvider {

    private static final String PROVIDER_NAME = "openai";

    private final OpenAiConfig config;
    private final HttpClient httpClient;

    public OpenAiProvider(OpenAiConfig config) {
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
        return model != null && model.startsWith("gpt");
    }

    @Override
    public LlmResponse generate(LlmRequest request) {
        try {
            String body = OpenAiRequestMapper.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(config.baseUrl() + "/chat/completions"))
                    .timeout(config.timeout())
                    .header("Authorization", "Bearer " + config.apiKey())
                    .header("User-Agent", "unopinionated-java-llm/0.1.0")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 401) {
                throw new LlmAuthenticationException(PROVIDER_NAME, "Invalid OpenAI API key");
            }

            if (response.statusCode() >= 400) {
                throw new LlmProviderException(
                        PROVIDER_NAME,
                        "OpenAI error: " + response.body()
                );
            }

            String text = OpenAiResponseParser.extractText(response.body());

            return new LlmResponse(
                    text,
                    null,
                    PROVIDER_NAME,
                    request.model()
            );

        } catch (LlmException e) {
            throw e;
        } catch (Exception e) {
            throw new LlmProviderException(PROVIDER_NAME, "OpenAI request failed", e);
        }
    }


    @Override
    public LlmStream stream(LlmRequest request) {
        try {
            String body = OpenAiRequestMapper.toJson(request, true);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(config.baseUrl() + "/chat/completions"))
                    .timeout(config.timeout())
                    .header("Authorization", "Bearer " + config.apiKey())
                    .header("User-Agent", "unopinionated-java-llm/0.1.0")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<java.io.InputStream> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

            return new OpenAiLlmStream(response);

        } catch (Exception e) {
            throw new com.sangamesh.llm.error.LlmProviderException(
                    PROVIDER_NAME,
                    "OpenAI streaming request failed",
                    e
            );
        }
    }

}
