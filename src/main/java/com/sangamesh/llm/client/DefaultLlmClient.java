package com.sangamesh.llm.client;

import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;
import com.sangamesh.llm.provider.LlmProvider;
import com.sangamesh.llm.stream.internal.BlockingLlmStream;

final class DefaultLlmClient implements LlmClient {

    private final LlmProvider provider;

    DefaultLlmClient(LlmProvider provider) {
        this.provider = provider;
    }

    @Override
    public LlmResponse generate(LlmRequest request) {
        return provider.generate(request);
    }

    @Override
    public Iterable<String> stream(LlmRequest request) {
        return new BlockingLlmStream(provider.stream(request));
    }
}

