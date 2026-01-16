package com.sangamesh.llm.provider;

import com.sangamesh.llm.client.LlmClient;
import com.sangamesh.llm.client.LlmClients;
import com.sangamesh.llm.model.LlmMessage;
import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.provider.gemini.GeminiConfig;
import com.sangamesh.llm.provider.gemini.GeminiProvider;
import org.junit.jupiter.api.Test;

class GeminiSmokeTest {

    @Test
    void shouldStreamUsingGemini() {
        GeminiConfig config = GeminiConfig.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .build();

        LlmClient client = LlmClients.builder()
                .provider(new GeminiProvider(config))
                .build();

        LlmRequest request = LlmRequest.builder()
                .model("gemini-2.5-flash")
                .addMessage(LlmMessage.user("Tell me about Donald Trump"))
                .build();

        for (String token : client.stream(request)) {
            System.out.print(token);
        }

        System.out.println("\n\n--- GEMINI STREAM COMPLETE ---");
    }

}
