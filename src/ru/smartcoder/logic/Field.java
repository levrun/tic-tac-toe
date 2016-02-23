package ru.smartcoder.logic;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private List<List<Cell>> cells = new ArrayList<>();

    private int rowsNum;
    private int colsNum;

    public Field(int rowsNum, int colsNum) {
        this.rowsNum = rowsNum;
        this.colsNum = colsNum;
        initCells();
    }

    public int getRowsNum() {
        return rowsNum;
    }

    public int getColsNum() {
        return colsNum;
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    private void initCells() {
        for (int i = 0; i < rowsNum; i++) {
            List<Cell> row = new ArrayList<>();
            cells.add(row);
            for (int j = 0; j < colsNum; j++) {
                row.add(new Cell());
            }
        }
    }

    public void resetCells() {
        cells.forEach(list -> list.forEach(((cell) -> cell.setValue(Cell.CELL_VALUE_EMPTY))));
    }

    private boolean checkRow(int rowId, String value) {
        boolean hasOnlyValues = true;

        List<Cell> row = cells.get(rowId);
        for (Cell cell : row) {
            if (!cell.getValue().equals(value)) {
                hasOnlyValues = false;
                break;
            }
        }

        return hasOnlyValues;
    }

    private boolean checkCol(int colId, String value) {
        boolean hasOnlyValues = true;

        for (int i = 0; i < rowsNum; i++) {
            if (!cells.get(i).get(colId).getValue().equals(value)) {
                hasOnlyValues = false;
                break;
            }
        }

        return hasOnlyValues;
    }

    private boolean checkMainDiagonal(String value) {
        boolean hasOnlyValues = true;

        for (int i = 0; i < rowsNum; i++) {
            if (i >= colsNum) {
                break;
            }

            if (!cells.get(i).get(i).getValue().equals(value)) {
                hasOnlyValues = false;
                break;
            }
        }

        return hasOnlyValues;
    }

    private boolean checkAuxDiagonal(String value) {
        boolean hasOnlyValues = true;

        int iMax = Math.min(rowsNum, colsNum);

        for (int i = 0; i < iMax; i++) {
            int colId = (iMax - i) - 1;

            if (!cells.get(i).get(colId).getValue().equals(value)) {
                hasOnlyValues = false;
                break;
            }
        }

        return hasOnlyValues;
    }

    public boolean checkGame(String value) {
        boolean isRow0 = (checkRow(0, value));
        boolean isRow1 = (checkRow(1, value));
        boolean isRow2 = (checkRow(2, value));

        boolean isCol0 = (checkCol(0, value));
        boolean isCol1 = (checkCol(1, value));
        boolean isCol2 = (checkCol(2, value));

        boolean isMainDiag = checkMainDiagonal(value);
        boolean isAuxDiag = checkAuxDiagonal(value);

        return (isRow0 || isRow1 || isRow2 || isCol0 || isCol1 || isCol2 || isMainDiag || isAuxDiag);
    }

    public boolean checkGame() {
        return checkGame(Cell.CELL_VALUE_X) || checkGame(Cell.CELL_VALUE_O);
    }

}