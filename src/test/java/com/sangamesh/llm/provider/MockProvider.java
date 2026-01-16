package com.sangamesh.llm.provider;

import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;
import com.sangamesh.llm.model.LlmUsage;
import com.sangamesh.llm.stream.LlmStream;

public class MockProvider implements LlmProvider {

    @Override
    public String name() {
        return "dummy";
    }

    @Override
    public boolean supports(String model) {
        return true;
    }

    @Override
    public LlmResponse generate(LlmRequest request) {
        return new LlmResponse(
                "Dummy response for model: " + request.model(),
                new LlmUsage(0, 0, 0),
                name(),
                request.model()
        );
    }

    @Override
    public LlmStream stream(LlmRequest request) {
        return new LlmStream() {

            @Override
            public void onNext(java.util.function.Consumer<String> tokenConsumer) {
                tokenConsumer.accept("Dummy ");
                tokenConsumer.accept("stream ");
                tokenConsumer.accept("response");
            }

            @Override
            public void onComplete(Runnable completionHandler) {
                completionHandler.run();
            }

            @Override
            public void onError(java.util.function.Consumer<Throwable> errorHandler) {
                // no-op
            }
        };
    }
}

