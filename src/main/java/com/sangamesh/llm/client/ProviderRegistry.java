package com.sangamesh.llm.client;

import com.sangamesh.llm.provider.LlmProvider;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

final class ProviderRegistry {

    private static final Map<String, LlmProvider> PROVIDERS = new ConcurrentHashMap<>();
    private static volatile boolean initialized = false;

    private ProviderRegistry() {}

    static void register(LlmProvider provider) {
        PROVIDERS.put(provider.name().toLowerCase(), provider);
    }

    static LlmProvider get(String name) {
        ensureInitialized();

        LlmProvider provider = PROVIDERS.get(name.toLowerCase());
        if (provider == null) {
            throw new IllegalArgumentException("No LLM provider registered: " + name);
        }
        return provider;
    }

    static void ensureInitialized() {
        if (initialized) return;

        synchronized (ProviderRegistry.class) {
            if (initialized) return;

            ServiceLoader<LlmProvider> loader =
                    ServiceLoader.load(LlmProvider.class);

            for (LlmProvider provider : loader) {
                register(provider);
            }

            initialized = true;
        }
    }

    static Iterable<LlmProvider> allProviders() {
        return PROVIDERS.values();
    }
}
