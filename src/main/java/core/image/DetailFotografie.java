package core.image;

import core.image.exceptions.InvalidDetailFotografie;

public enum DetailFotografie {
    NULL, FUNKTION, SORTIMENTSDETAIL, MATERIALSTOFF, FARBFLECK, DIGITALESSTOFFMUSTER, MARKENDETAIL, ZUBEHOER;

    public boolean isMirrorable() {
        switch (this) {
            case FUNKTION:
            case NULL:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case FUNKTION:
                return "Funktion";
            case SORTIMENTSDETAIL:
                return "Sortimentsdetail";
            case MATERIALSTOFF:
                return "Material / Stoff";
            case FARBFLECK:
                return "Farbfleck";
            case DIGITALESSTOFFMUSTER:
                return "Digitales Stoffmuster";
            case MARKENDETAIL:
                return "Markendetail";
            case NULL:
                return "";
            case ZUBEHOER:
                return "Zubehör";
            default:
                return null;
        }
    }

    public int getOrder() throws InvalidDetailFotografie {
        switch (this) {
            case NULL:
                return 0;
            case FUNKTION:
                return 1;
            case SORTIMENTSDETAIL:
                return 2;
            case FARBFLECK:
                return 5;
            case DIGITALESSTOFFMUSTER:
                return 6;
            case MARKENDETAIL:
                return 7;
            case MATERIALSTOFF:
                return 8;
            case ZUBEHOER:
                return 9;
            default:
                throw new InvalidDetailFotografie(this);
        }
    }
}
