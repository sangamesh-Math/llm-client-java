//package com.sangamesh.llm.provider;
//
//import com.sangamesh.llm.client.LlmClient;
//import com.sangamesh.llm.client.LlmClients;
//import com.sangamesh.llm.model.LlmMessage;
//import com.sangamesh.llm.model.LlmRequest;
//import com.sangamesh.llm.model.LlmResponse;
//import com.sangamesh.llm.provider.claude.ClaudeConfig;
//import com.sangamesh.llm.provider.claude.ClaudeProvider;
//import org.junit.jupiter.api.Test;
//
//public class ClaudeSmokeTest {
//    @Test
//    void shouldGenerateResponseUsingClaude() {
//        LlmClient client = LlmClients.builder()
//                .provider(new ClaudeProvider(
//                        ClaudeConfig.builder()
//                                .apiKey(System.getenv("CLAUDE_API_KEY"))
//                                .build()
//                ))
//                .build();
//
//        LlmResponse response = client.generate(
//                LlmRequest.builder()
//                        .model("claude-3-haiku-20240307")
//                        .addMessage(LlmMessage.user("Write a haiku about JVMs"))
//                        .build()
//        );
//
//        System.out.println(response.text());
//    }
//
//}
