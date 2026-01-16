package com.sangamesh.llm.stream;

import java.util.function.Consumer;

public interface LlmStream {

    void onNext(Consumer<String> tokenConsumer);

    void onComplete(Runnable completionHandler);

    void onError(Consumer<Throwable> errorHandler);
}
