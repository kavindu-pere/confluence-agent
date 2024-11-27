package com.cypher.etl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cypher.etl.models.Page;
import com.cypher.etl.models.PageResult;
import com.cypher.etl.models.SpaceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfluenceELTService {

    private final RestClient restClient;
    private final VectorStore vectorStore;

    private final ObjectMapper objectMapper;

    // @PostConstruct
    private void extract() {
        SpaceResult spaces = getSpaces();
        spaces.getResults().forEach(space -> {
            PageResult pageResult = getPages(space.getId());
            pageResult.getResults().stream()
                    .filter(p -> !p.getBody().getStorage().getValue().isBlank())
                    .map(this::transformAndLoad)
                    .forEach(docs -> vectorStore.write(docs));
        });
    }

    private List<Document> transformAndLoad(Page page) {
        var stringPageJSONView = "";
        try {
            stringPageJSONView = objectMapper.writeValueAsString(page);
        } catch (JsonProcessingException e) {
            log.error("Error converting page to JSON", e);
        }
        log.info("Page JSON: {}", stringPageJSONView);
        Resource toResource = new ByteArrayResource(stringPageJSONView.getBytes());
        var tikaDocs = new TikaDocumentReader(toResource).read();
        
        TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        return splitter.split(tikaDocs);
    }

    public PageResult getPages(String spaceId) {
        return restClient.get()
                .uri("/wiki/api/v2/pages?body-format=storage&status=current&space-id={spaceId}", spaceId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(PageResult.class);
    }

    public SpaceResult getSpaces() {
        return restClient.get()
                .uri("/wiki/api/v2/spaces?type=global")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(SpaceResult.class);
    }
}
