package ru.smartcoder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.smartcoder.logic.AdvancedComputer;
import ru.smartcoder.logic.Cell;
import ru.smartcoder.logic.Computer;
import ru.smartcoder.logic.Field;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Application {

    public static final String GAME_OVER = "Game Over";
    public static final String YOU_WON = "You won!";
    public static final String YOU_LOST = "You lost!";
    public static final String CONGRATS = "Congratulations!";
    public static final String TRY_AGAIN = "Try again!";

    private static int CELLS_X = 3;
    private static int CELLS_Y = 3;

    private static double CELL_MIN_HEIGHT = 50;
    private static double CELL_PREF_HEIGHT = 100;
    private static double CELL_MAX_HEIGHT = 100;

    private static double CELL_MIN_WIDTH = 50;
    private static double CELL_PREF_WIDTH = 100;
    private static double CELL_MAX_WIDTH = 100;

    private static double CELL_FONT_SIZE = 23;

    private static double CELL_HORIZONTAL_GAP = 15;
    private static double CELL_VERTICAL_GAP = 15;

    // DONE - TODO заменить на использование списков List и ArrayList
    private List<List<Button>> buttons = new ArrayList<>();

    private Field field;

    private Label label;

    private boolean isGameOver;

    private Computer computer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Создаем объект класса GridPane - менеджер расположения на котором будут лежать наши клетки для игры в виде grid.
        GridPane board = new GridPane();

        // Задаем величину разрыва между колонками в grid, для того, чтобы ячейки не сливались.
        board.setHgap(CELL_HORIZONTAL_GAP);
        board.setVgap(CELL_VERTICAL_GAP);

        field = new Field(CELLS_Y, CELLS_X);
        field.resetCells();
        isGameOver = false;

        computer = new AdvancedComputer(field, Cell.CELL_VALUE_O, Cell.CELL_VALUE_X);

        initButtons();
        redrawButtons();
        addButtons(board);

        // layout
        label = new Label("Welcome!");

        Button resetButton = new Button("Play again");
        resetButton.setOnAction(event -> {
            resetGame();
            label.setText("Game field was reset");
        });

        // Создаем панель root, содержащую игровое поле и элементы управления
        BorderPane root = new BorderPane() {{
            VBox bottomPanel = new VBox(5, label, resetButton);
            bottomPanel.setAlignment(Pos.CENTER);
            BorderPane.setMargin(bottomPanel, new Insets(20));

            setCenter(board);
            setBottom(bottomPanel);

            setMargin(board, new Insets(25));
            setMaxWidth(375);
            setMaxHeight(450);
        }};

        // Создаем сцену, содержащую панель root
        Scene scene = new Scene(root, 375, 450);

        URL url = getClass().getResource("main.css");
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);

        // Устанавливаем параметры окна, его содержимое и заголок и показываем
        primaryStage.setMaxWidth(375);
        primaryStage.setMinWidth(300);
        primaryStage.setMaxHeight(500);
        primaryStage.setMinHeight(400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    private void initButtons() {
        for (int i = 0; i < CELLS_Y; i++) {
            List<Button> row = new ArrayList<>();
            buttons.add(row);
            for (int j = 0; j < CELLS_X; j++) {
                row.add(new Button() {{
                    setMinHeight(CELL_MIN_HEIGHT);
                    setPrefHeight(CELL_PREF_HEIGHT);
                    setMaxHeight(CELL_MAX_HEIGHT);
                    setMinWidth(CELL_MIN_WIDTH);
                    setPrefWidth(CELL_PREF_WIDTH);
                    setMaxWidth(CELL_MAX_WIDTH);
                    setFont(new Font(CELL_FONT_SIZE));
                }});

                final int rowId = i;
                final int colId = j;

                // DONE - TODO заменить на лямбду как в кнопке resetButton
                buttons.get(rowId).get(colId).setOnAction(event -> {
                    if (isGameOver) { return; }

                    Cell cell = field.getCells().get(rowId).get(colId);
                    boolean isEmpty = cell.getValue().equals(Cell.CELL_VALUE_EMPTY);
                    if (!isEmpty) { return; }

                    label.setText("");

                    // Player's move
                    cell.setValue(Cell.CELL_VALUE_X);
                    redrawButtons();
                    isGameOver = field.checkGame();

                    if (isGameOver) {
                        onGameOver(field);
                        return;
                    }

                    // Computer's move
                    computer.play();
                    redrawButtons();
                    isGameOver = field.checkGame();

                    if (isGameOver) {
                        onGameOver(field);
                    }
                });
            }
        }
    }

    private void onGameOver(Field field) {
        String headerText = (field.checkGame(Cell.CELL_VALUE_X) ? YOU_WON : YOU_LOST);
        String contentText = (field.checkGame(Cell.CELL_VALUE_X) ? CONGRATS : TRY_AGAIN);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(GAME_OVER);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();

        label.setText(GAME_OVER);
    }

    private void addButtons(GridPane board) {
        // TODO заменить на работу с лямбдой и forEach
        int[] y = { 0 };
        buttons.forEach(row -> {
            int[] x = { 0 };
            row.forEach(((button) -> {
                board.add(buttons.get(y[0]).get(x[0]), x[0]++, y[0]);
            }));
            y[0]++;
        });
    }

    private void resetGame() {
        field.resetCells();
        redrawButtons();
        isGameOver = false;
    }

    private void redrawButtons() {
        for (int y = 0; y < buttons.size(); y++) {
            for (int x = 0; x < buttons.get(y).size(); x++) {
                Cell cell = field.getCells().get(y).get(x);
                buttons.get(y).get(x).setText(cell.getValue());
            }
        }
    }

    public static void run(String[] args) {
        Application.launch(args);
    }

}