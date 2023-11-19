package frontline.persistence;

import java.util.InputMismatchException;

public enum StaticsType {
    Barrack1('k'),
    Barrack2('p'),
    Forest('f'),
    Swamp('s'),
    Castle1('#'),
    Castle2('@'),
    Empty('-');

    public final char value;

    StaticsType(char s){
        value = s;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public static StaticsType getInstance(char c){
        for (StaticsType tt : StaticsType.values()){
            if (tt.value == c) return tt;
        }
        throw new InputMismatchException();
    }
}