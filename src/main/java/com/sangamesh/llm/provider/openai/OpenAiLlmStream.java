package com.sangamesh.llm.provider.openai;

import com.sangamesh.llm.stream.LlmStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

final class OpenAiLlmStream implements LlmStream {

    private final HttpResponse<java.io.InputStream> response;

    private Consumer<String> onNext;
    private Runnable onComplete;
    private Consumer<Throwable> onError;

    OpenAiLlmStream(HttpResponse<java.io.InputStream> response) {
        this.response = response;
    }

    @Override
    public void onNext(Consumer<String> tokenConsumer) {
        this.onNext = tokenConsumer;
        startIfReady();
    }

    @Override
    public void onComplete(Runnable completionHandler) {
        this.onComplete = completionHandler;
        startIfReady();
    }

    @Override
    public void onError(Consumer<Throwable> errorHandler) {
        this.onError = errorHandler;
        startIfReady();
    }

    private void startIfReady() {
        if (onNext == null || onComplete == null || onError == null) {
            return;
        }

        new Thread(this::readStream, "openai-llm-stream").start();
    }

    private void readStream() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.body())
        )) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String data = line.substring(6);

                    if ("[DONE]".equals(data)) {
                        onComplete.run();
                        return;
                    }

                    String token = OpenAiStreamParser.extractDelta(data);
                    if (!token.isEmpty()) {
                        onNext.accept(token);
                    }
                }
            }
        } catch (Exception e) {
            onError.accept(e);
        }
    }
}
