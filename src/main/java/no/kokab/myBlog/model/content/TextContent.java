package no.kokab.myBlog.model.content;

public record TextContent(String title, String text) implements Content {

    @Override
    public String title() {
        return title;
    }

    public String text() {
        return text;
    }
}
