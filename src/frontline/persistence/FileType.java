package frontline.persistence;

import java.util.InputMismatchException;

public enum FileType {
    Level1("Pálya 1"),
    Level2("Pálya 2"),
    Level3("Pálya 3"),
    Level4("Pálya 4"),
    Level5("Pálya 5"),
    Level6("Pálya 6");

    public final String value;

    FileType(String s) {
        value = s;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public static FileType getInstance(String c) {
        for (FileType tt : FileType.values()) {
            if (tt.value == c) return tt;
        }
        throw new InputMismatchException();
    }
}
