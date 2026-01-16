//package com.sangamesh.llm.provider;
//
//import com.sangamesh.llm.client.LlmClient;
//import com.sangamesh.llm.client.LlmClients;
//import com.sangamesh.llm.model.LlmMessage;
//import com.sangamesh.llm.model.LlmRequest;
//import com.sangamesh.llm.provider.openai.OpenAiConfig;
//import com.sangamesh.llm.provider.openai.OpenAiProvider;
//import com.sangamesh.llm.stream.LlmStream;
//import org.junit.jupiter.api.Test;
//
//class OpenAiStreamingSmokeTest {
//
//    @Test
//    void shouldStreamTokensFromOpenAi() {
//        OpenAiProvider provider = new OpenAiProvider(
//                OpenAiConfig.builder()
//                        .apiKey(System.getenv("OPENAI_API_KEY"))
//                        .build()
//        );
//        System.out.println("Dummy message trigger to the console.");
//        System.out.println("API KEY PRESENT = " + (System.getenv("OPENAI_API_KEY") != null));
//
//        LlmClient client = LlmClients.builder()
//                .provider(provider)
//                .build();
//
//        LlmRequest request = LlmRequest.builder()
//                .model("gpt-4.1-mini")
//                .addMessage(LlmMessage.user("Write a haiku about Java streams"))
//                .build();
//
//        LlmStream stream = (LlmStream) client.stream(request);
//
//        stream.onNext(token -> System.out.print(token));
//        stream.onError(Throwable::printStackTrace);
//        stream.onComplete(() -> System.out.println("\n\n--- STREAM COMPLETE ---"));
//    }
//}
