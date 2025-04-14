package no.kokab.myBlog.model.content;

public sealed interface Content permits TextContent, ImageContent {
    String title();

}

