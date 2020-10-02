package core.article.exceptions;

import java.util.Arrays;

public class UnknownMirrorInformation extends InvalidArticleData {
    public UnknownMirrorInformation(String[] variantMirrorInformation) {
        super("Could not handle mirror information: " + Arrays.toString(variantMirrorInformation));
    }
}
