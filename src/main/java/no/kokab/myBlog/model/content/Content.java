package no.kokab.myBlog.model.content;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)

@JsonSubTypes({
    @JsonSubTypes.Type(value = TextContent.class, name = "TEXT"),
    @JsonSubTypes.Type(value = ImageContent.class, name = "IMAGE")
})

public sealed interface Content permits TextContent, ImageContent {
    String type();
    String title();
}

