package ru.smartcoder.logic;

public class Cell {

    public static final String CELL_VALUE_X = "x";
    public static final String CELL_VALUE_O = "o";
    public static final String CELL_VALUE_EMPTY = " ";

    private String value = CELL_VALUE_EMPTY;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}