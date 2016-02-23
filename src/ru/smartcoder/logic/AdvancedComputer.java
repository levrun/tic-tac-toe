package ru.smartcoder.logic;

public class AdvancedComputer extends SimpleComputer {

    public AdvancedComputer(Field field, String computerMarker, String opponentMarker) {
        super(field, computerMarker, opponentMarker);
    }

    @Override
    public void play() {
        if (tryWinningMove()) {
            return;
        }

        if (tryDefendingMove()) {
            return;
        }

        if (tryCentralMove()) {
            return;
        }

        super.play();
    }

    protected boolean tryWinningMove() {
        for (int row = 0; row < field.getRowsNum(); row++) {
            for (int col = 0; col < field.getColsNum(); col++) {
                Cell cell = field.getCells().get(row).get(col);
                if (cell.getValue().equals(Cell.CELL_VALUE_EMPTY)) {
                    cell.setValue(computerMarker);

                    if (field.checkGame(computerMarker)) {
                        return true;
                    } else {
                        cell.setValue(Cell.CELL_VALUE_EMPTY);
                    }
                }
            }
        }

        return false;
    }

    protected boolean tryDefendingMove() {
        for (int row = 0; row < field.getRowsNum(); row++) {
            for (int col = 0; col < field.getColsNum(); col++) {
                Cell cell = field.getCells().get(row).get(col);
                if (cell.getValue().equals(Cell.CELL_VALUE_EMPTY)) {
                    cell.setValue(opponentMarker);

                    if (field.checkGame(opponentMarker)) {
                        cell.setValue(computerMarker);
                        return true;
                    } else {
                        cell.setValue(Cell.CELL_VALUE_EMPTY);
                    }
                }
            }
        }

        return false;
    }

    protected boolean tryCentralMove() {
        boolean hasCenter = ((field.getRowsNum() % 2 == 1) && (field.getColsNum() % 2) == 1);

        if (hasCenter) {
            int centerRow = field.getRowsNum() / 2;
            int centerCol = field.getColsNum() / 2;
            Cell centerCell = field.getCells().get(centerRow).get(centerCol);
            if (centerCell.getValue().equals(Cell.CELL_VALUE_EMPTY)) {
                centerCell.setValue(computerMarker);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
