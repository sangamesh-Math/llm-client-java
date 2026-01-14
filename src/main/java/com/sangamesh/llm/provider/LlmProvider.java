package com.sangamesh.llm.provider;

import com.sangamesh.llm.model.LlmRequest;
import com.sangamesh.llm.model.LlmResponse;
import com.sangamesh.llm.stream.LlmStream;

public interface LlmProvider {

    String name();

    boolean supports(String model);

    LlmResponse generate(LlmRequest request);

    LlmStream stream(LlmRequest request);
}

