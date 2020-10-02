package infrastructure.m2.schema;

@SuppressWarnings("unused")
public class M2Root {
    private final String href;

    public M2Root(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return href;
    }
}
