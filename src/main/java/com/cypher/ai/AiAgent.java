package com.cypher.ai;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

import java.time.LocalDateTime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AiAgent {

    private final ChatClient chatClient;

    public AiAgent(ChatClient.Builder chatClient, ChatMemory chatMemory, VectorStore vectorStore) {
        this.chatClient = chatClient
                .defaultSystem("""
                            You are Samantha, a support agent for Confluence at Wiley.
                            You have access to the knowledge base of the Wiley's confluence pages and you can
                            search anything available in Wiley's confluence pages.
                            For any queries, you are not allowed to search the web.
                            You respond to the user's queries with the help of the knowledge base.
                            You respond in a friendly and professional manner.
                            You are interacting with the user through a chat interface.
                            Today is {current_date}.
                        """)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .build();
    }

    public Flux<String> chat(String chatId, String userMessage) {

        return chatClient.prompt()
                .system(sys -> sys.param("current_date", LocalDateTime.now().toString()))
                .user(userMessage)
                .advisors(ads -> ads
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .stream()
                .content();
    }

}
