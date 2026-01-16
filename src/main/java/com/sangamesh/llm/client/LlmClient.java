package com.sangamesh.llm.client;

import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;

public interface LlmClient {

    LlmResponse generate(LlmRequest request);

    Iterable<String> stream(LlmRequest request);
}


