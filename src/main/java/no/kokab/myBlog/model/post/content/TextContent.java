package no.kokab.myBlog.model.post.content;

public record TextContent(String type, String title, String text) implements Content {

    @Override
    public String type() {
        return type;
    }

    @Override
    public String title() {
        return title;
    }

    public String text() {
        return text;
    }
}
