package core.article.cgi;

import core.article.MirrorInformation;
import core.article.exceptions.UnknownMirrorInformation;

public class CgiPolsterMoebelMirrorInformation implements MirrorInformation {

    private boolean isMirror = false;
    private final String[] mirrorComponents = new String[]{
            "Ottomane",
            "Recamiere",
            "Schenkel",
            "Longchair"
    };

    public CgiPolsterMoebelMirrorInformation(String directionString) throws UnknownMirrorInformation {
        if (directionString != null) {
            validateDirectionString(directionString);
            setMirror(directionString);
        }
    }

    @Override
    public boolean isMirror() {
        return isMirror;
    }

    private void setMirror(String string) {
        isMirror = string.contains("rechts");
    }

    private void validateDirectionString(String string) throws UnknownMirrorInformation {
        if ((containsDirection(string) && !(containsMirrorComponent(string)))
                || (containsMirrorComponent(string) && !(containsDirection(string))))
            throw new UnknownMirrorInformation(new String[]{string});
    }

    private boolean containsMirrorComponent(String string) {
        for (String mirrorComponent : mirrorComponents)
            if (string.contains(mirrorComponent))
                return true;
        return false;
    }

    private boolean containsDirection(String string) {
        return string.contains("rechts") || string.contains("links") || string.contains("beidseitig");
    }
}
