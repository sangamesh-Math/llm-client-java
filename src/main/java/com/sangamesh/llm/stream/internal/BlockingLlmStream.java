package com.sangamesh.llm.stream.internal;

import com.sangamesh.llm.stream.LlmStream;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class BlockingLlmStream implements Iterable<String> {

    private static final Object END = new Object();

    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean completed = new AtomicBoolean(false);
    private volatile Throwable error;

    public BlockingLlmStream(LlmStream source) {
        source.onNext(token -> queue.add(token));
        source.onComplete(() -> {
            completed.set(true);
            queue.add(END);
        });
        source.onError(t -> {
            error = t;
            completed.set(true);
            queue.add(END);
        });
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {

            private Object next;

            @Override
            public boolean hasNext() {
                if (next == END) return false;
                if (next != null) return true;

                try {
                    next = queue.take();
                    if (next == END && error != null) {
                        throw new RuntimeException("LLM stream failed", error);
                    }
                    return next != END;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object value = next;
                next = null;
                return (String) value;
            }
        };
    }
}
