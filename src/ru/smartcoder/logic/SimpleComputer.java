package ru.smartcoder.logic;

public class SimpleComputer implements Computer {

    protected Field field;
    protected String computerMarker;
    protected String opponentMarker;

    public SimpleComputer(Field field, String computerMarker, String opponentMarker) {
        this.field = field;
        this.computerMarker = computerMarker;
        this.opponentMarker = opponentMarker;
    }

    protected static boolean hasEmptyCells(Field field) {
        return field.getCells().stream().anyMatch(list ->
                        list.stream().anyMatch(cell ->
                                        cell.getValue().equals(Cell.CELL_VALUE_EMPTY)
                        )
        );
    }

    @Override
    public void play() {
        if (!hasEmptyCells(field)) { return; }

        int rowId, colId;
        Cell cell;
        boolean isFound = false;

        do {
            rowId = (int) Math.floor(Math.random() * field.getRowsNum());
            colId = (int) Math.floor(Math.random() * field.getColsNum());

            cell = field.getCells().get(rowId).get(colId);

            isFound = cell.getValue().equals(Cell.CELL_VALUE_EMPTY);
        } while (!isFound);

        cell.setValue(computerMarker);
    }

}