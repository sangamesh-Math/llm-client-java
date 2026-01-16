package com.sangamesh.llm.client;

import com.sangamesh.llm.provider.LlmProvider;

final class ModelRouter {

    private ModelRouter() {}

    static LlmProvider route(String model) {
        ProviderRegistry.ensureInitialized();

        for (LlmProvider provider : ProviderRegistry.allProviders()) {
            if (provider.supports(model)) {
                return provider;
            }
        }

        throw new IllegalArgumentException(
                "No provider found supporting model: " + model
        );
    }
}
