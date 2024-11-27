package com.cypher.vectorstore;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class VectorStoreService {

    @Autowired
    private VectorStore vectorStore;

    @PostConstruct
    public void addDocuments() {
        List<Document> documents = List.of(
            new Document("Exploring the depths of AI with Spring", Map.of("category", "AI")),
            new Document("Navigating through large datasets efficiently"),
            new Document("Harnessing the power of vector search", Map.of("importance", "high"))
        );

        vectorStore.add(documents);
    }       

}
