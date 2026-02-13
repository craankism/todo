package src;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private FlowPane rootPane;

    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final List<TextField> textFields = new ArrayList<>();

    @FXML
    private void initialize() {
        addNewRow();
    }

    private void addNewRow() {
        CheckBox checkBox = new CheckBox();
        TextField textField = new TextField();

        textField.setPrefHeight(26.0);
        textField.setPrefWidth(518.0);
        FlowPane.setMargin(textField, new Insets(0, 0, 0, 20.0));

        // Crossout: toggle strikethrough when checkbox is checked
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                textField.getStyleClass().add("strikethrough");
            } else {
                textField.getStyleClass().remove("strikethrough");
            }
        });

        // Auto-add / auto-remove rows based on text changes
        textField.textProperty().addListener((obs, oldText, newText) -> {
            int index = textFields.indexOf(textField);
            boolean isLastRow = (index == textFields.size() - 1);

            if (isLastRow && !newText.isEmpty()) {
                addNewRow();
            }

            if (!isLastRow && newText.isEmpty()) {
                Platform.runLater(() -> removeRow(textField));
            }
        });

        checkBoxes.add(checkBox);
        textFields.add(textField);
        rootPane.getChildren().addAll(checkBox, textField);
    }

    private void removeRow(TextField textField) {
        int index = textFields.indexOf(textField);
        if (index < 0) return;

        CheckBox cb = checkBoxes.remove(index);
        TextField tf = textFields.remove(index);
        rootPane.getChildren().removeAll(cb, tf);
    }
}
