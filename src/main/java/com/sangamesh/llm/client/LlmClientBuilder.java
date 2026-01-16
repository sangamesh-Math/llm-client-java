package com.sangamesh.llm.client;

import com.sangamesh.llm.provider.LlmProvider;

public final class LlmClientBuilder {

    private LlmProvider provider;
    private String providerName;

    public LlmClientBuilder provider(LlmProvider provider) {
        this.provider = provider;
        return this;
    }

    public LlmClientBuilder provider(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public LlmClient build() {
        if (provider == null && providerName == null) {
            throw new IllegalStateException("Either provider or providerName must be set");
        }

        if (provider != null) {
            ProviderRegistry.register(provider);
            return new DefaultLlmClient(provider);
        }

        LlmProvider resolved = ProviderRegistry.get(providerName);
        return new DefaultLlmClient(resolved);
    }
}
