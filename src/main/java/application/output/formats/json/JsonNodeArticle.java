package application.output.formats.json;

@SuppressWarnings("unused")
public class JsonNodeArticle {
    private final String articleNumber;
    private final String name;

    public JsonNodeArticle(String articleNumber, String name) {
        this.articleNumber = articleNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!JsonNodeArticle.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        JsonNodeArticle other = (JsonNodeArticle) obj;
        return this.articleNumber.equals(other.articleNumber)
                && this.name.equals(other.name);
    }
}
