package application.output.formats.json;

@SuppressWarnings("unused")
class JsonNodeMaterialAttributes {
    private final String name;
    private final String values;

    public JsonNodeMaterialAttributes(String name, String values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public String getValues() {
        return values;
    }
}
