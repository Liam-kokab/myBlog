package no.kokab.myBlog.model.post.content;

public record ImageContent(String type, String title, String text, String url) implements Content {
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

    public String url() {
        return url;
    }
}
