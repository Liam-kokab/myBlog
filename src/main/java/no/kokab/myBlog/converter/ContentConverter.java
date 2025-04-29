package no.kokab.myBlog.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import no.kokab.myBlog.model.post.content.Content;

import java.util.List;

@Converter
public class ContentConverter implements AttributeConverter<List<Content>, String> {

    private final ObjectMapper objectMapper;

    public ContentConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(List<Content> content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert content to JSON", e);
        }
    }

    @Override
    public List<Content> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to content", e);
        }
    }
}