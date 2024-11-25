package com.cypher.client;

import com.cypher.ai.AiAgent;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import reactor.core.publisher.Flux;

@BrowserCallable
@AnonymousAllowed
public class AgentService {

    private final AiAgent aiAgent;

    public AgentService(AiAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    public Flux<String> chat(String chatId, String userMessage) {
        return aiAgent.chat(chatId, userMessage);
    }

}
