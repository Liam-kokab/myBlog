package no.kokab.myBlog.model.content;

public record ImageContent(String title, String text, String url) implements Content {
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
