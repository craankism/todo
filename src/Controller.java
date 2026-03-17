package src;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private FlowPane flowPane;

    @FXML
    void save(ActionEvent event) {
        save();
    }

    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final List<TextField> textFields = new ArrayList<>();

    @FXML
    private void initialize() {
        load();
        addNewRow();
    }

    private void addNewRow() {
        CheckBox checkBox = new CheckBox();
        TextField textField = new TextField();

        textField.setPrefHeight(26.0);
        textField.setPrefWidth(530.0);
        FlowPane.setMargin(textField, new Insets(10.0, 0, 0, 20.0));
        FlowPane.setMargin(checkBox, new Insets(10.0, 0, 0, 0));

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
        flowPane.getChildren().addAll(checkBox, textField);
    }

    private void removeRow(TextField textField) {
        int index = textFields.indexOf(textField);
        if (index < 0)
            return;

        CheckBox cb = checkBoxes.remove(index);
        TextField tf = textFields.remove(index);
        flowPane.getChildren().removeAll(cb, tf);
    }

    private void save() {

        try (BufferedWriter textWriter = new BufferedWriter(new FileWriter("save.txt"))) {
            for (int i = 0; i < textFields.size(); i++) {
                textWriter.write(Boolean.toString(checkBoxes.get(i).isSelected()));
                textWriter.newLine();
                textWriter.write(textFields.get(i).getText());
                textWriter.newLine();
            }
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    private void load() {
        try (BufferedReader textReader = new BufferedReader(new FileReader("save.txt"))) {
            String lineCheckbox, lineText;
            int i = 0;
            while ((textReader.readLine()) != null) {
                lineCheckbox = textReader.readLine();
                lineText = textReader.readLine();
                if (lineText.trim().isEmpty())
                    continue;
                System.out.println(lineCheckbox);
                System.out.println(lineText);
                addNewRow();
                textReader.readLine();
                checkBoxes.get(i).setSelected(Boolean.parseBoolean(lineCheckbox));
                textFields.get(i).setText(lineText);
                i++;
            }
            System.out.println("Successfully read file.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
