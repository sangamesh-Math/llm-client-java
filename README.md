=====================================================================LLM Client for Java========================================================================================

A provider-neutral Java client for working with Large Language Models (LLMs) such as Google Gemini, OpenAI GPT, and Anthropic Claude using a single, clean API.

This library abstracts away vendor-specific SDKs and HTTP details, allowing Java developers to switch LLM providers with minimal code changes. 

   Features:
   
  1. Unified API for multiple LLM providers

  2. Provider-agnostic request/response model

  3. Pluggable provider architecture

  4. Zero external SDK dependencies

  5. Java 21 compatible

  6. Maven Central ready

==============================================================================================
Installation:

<dependency>
    <groupId>io.github.sangamesh-math</groupId>
    <artifactId>llm-client-java</artifactId>
    <version>1.0.0</version>
</dependency>

================================================================================================
Architecture Overview:
================================================================================================
Application Code
       |
       v
   LlmClient
       |
       v
   LlmProvider (SPI)
       |
       +--> GeminiProvider
       +--> OpenAiProvider
       +--> ClaudeProvider
================================================================================================

Quick Start (Gemini Example):
  Note: You need to generate a Gemini Api key from google ai studio and set it in environment variables!
  
GeminiConfig config = GeminiConfig.builder()
    .apiKey(System.getenv("GEMINI_API_KEY"))
    .build();

LlmClient client = LlmClients.builder()
    .provider(new GeminiProvider(config))
    .build();

LlmRequest request = LlmRequest.builder()
    .model("gemini-2.5-flash")
    .addMessage(LlmMessage.user("What is Java 21?"))
    .build();

LlmResponse response = client.generate(request);

System.out.println(response.text());

============================================================================================


Switching Providers (No Code Changes)

Only change the provider wiring:

LlmClient client = LlmClients.builder()
    .provider(new OpenAiProvider(openAiConfig))
    .build();

============================================================================================


Testing Philosophy

Provider smoke tests verify real API integration

Tests requiring API keys are:

Disabled by default

Intended for local/manual execution

CI remains provider-agnostic and deterministic
Why This Library Exists
Without this library:

You manually integrate each LLM provider

You maintain multiple SDKs

Each provider has:

Different request formats

Different response formats

Different error handling

Switching providers requires rewriting business logic

With this library:

One stable interface

One request/response model

Provider swap = config change

Clean separation between:

Business logic

LLM vendor details


====================================================================
Requirements

Java 21+
  and
Maven 3.9+
====================================================================


This library is designed for engineers building serious Java systems who want:

1. Control

2. Clean abstractions

3. Long-term maintainability

If you’ve ever rewritten LLM integrations just to switch providers, this library is for you.


========================================================================

Contributions are welcome!!
