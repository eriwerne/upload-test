package core.image.exceptions;

import core.image.DetailFotografie;

public class InvalidDetailFotografie extends Exception {
    public InvalidDetailFotografie(DetailFotografie detailFotografie) {
        super(detailFotografie.toString());
    }
}
