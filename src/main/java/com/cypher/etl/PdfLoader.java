package com.cypher.etl;

import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfLoader {

    private final VectorStore vectorStore;

    @Value("classpath:data/*.pdf")
    private Resource[] resources;

    @PostConstruct
    public void init() {
        var readerConfig = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(
                        new ExtractedTextFormatter.Builder()
                                .withNumberOfBottomTextLinesToDelete(0)
                                .withNumberOfTopPagesToSkipBeforeDelete(0)
                                .build())
                .withPagesPerDocument(1)
                .build();

        for (Resource pdfResource : resources) {
            try {
                processPdf(pdfResource, readerConfig);
                log.info("Processed PDF: {}", pdfResource.getFilename());
            } catch (Exception e) {
                log.error("Error processing PDF {}: {}", pdfResource.getFilename(), e.getMessage());
            }
        }
    }

    private void processPdf(Resource pdfResource, PdfDocumentReaderConfig readerConfig) {
        var reader = new PagePdfDocumentReader(pdfResource, readerConfig);
        var splitter = new TokenTextSplitter();
        vectorStore.write(splitter.transform(reader.read()));
    }
}
