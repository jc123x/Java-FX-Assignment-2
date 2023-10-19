
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class assignment2 extends Application {

    private TextField nameField, phoneField;
    private ComboBox<String> cakeComboBox;
    private RadioButton smallRadio, mediumRadio, largeRadio;
    private CheckBox deliveryCheckBox;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bakery Order App");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Name
        gridPane.add(new Label("Name:"), 0, 0);
        nameField = new TextField();
        gridPane.add(nameField, 1, 0);

        // Phone Number
        gridPane.add(new Label("Phone Number:"), 0, 1);
        phoneField = new TextField();
        gridPane.add(phoneField, 1, 1);

        // Cake Type
        gridPane.add(new Label("Cake Type:"), 0, 2);
        String[] cakeTypes = {"Apple", "Carrot", "Cheesecake", "Chocolate", "Coffee", "Opera", "Tiramisu"};
        cakeComboBox = new ComboBox<>();
        cakeComboBox.getItems().addAll(cakeTypes);
        gridPane.add(cakeComboBox, 1, 2);

        // Cake Size
        gridPane.add(new Label("Cake Size:"), 0, 3);
        ToggleGroup sizeGroup = new ToggleGroup();
        smallRadio = new RadioButton("Small");
        smallRadio.setToggleGroup(sizeGroup);
        mediumRadio = new RadioButton("Medium");
        mediumRadio.setToggleGroup(sizeGroup);
        largeRadio = new RadioButton("Large");
        largeRadio.setToggleGroup(sizeGroup);
        HBox sizeBox = new HBox(10, smallRadio, mediumRadio, largeRadio);
        gridPane.add(sizeBox, 1, 3);

        // Delivery
        gridPane.add(new Label("Free Delivery:"), 0, 4);
        deliveryCheckBox = new CheckBox();
        gridPane.add(deliveryCheckBox, 1, 4);

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveOrder());
        gridPane.add(saveButton, 0, 5);

        // Quit Button
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> stage.close());
        gridPane.add(quitButton, 1, 5);

        Scene scene = new Scene(gridPane, 400, 250);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Clean up resources if needed
    }

    private void saveOrder() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String cakeType = cakeComboBox.getValue();
        String cakeSize = "";
        if (smallRadio.isSelected()) {
            cakeSize = "Small";
        } else if (mediumRadio.isSelected()) {
            cakeSize = "Medium";
        } else if (largeRadio.isSelected()) {
            cakeSize = "Large";
        }
        boolean isDelivery = deliveryCheckBox.isSelected();

        // Write order to file
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("Orders.txt", true))) {
            writer.write("Name: " + name + "\n");
            writer.write("Phone Number: " + phone + "\n");
            writer.write("Cake Type: " + cakeType + "\n");
            writer.write("Cake Size: " + cakeSize + "\n");
            writer.write("Free Delivery: " + isDelivery + "\n");
            writer.write("--------------------\n");
            writer.newLine();
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Saved");
            alert.setHeaderText(null);
            alert.setContentText("Order saved successfully!");
            alert.showAndWait();
            clearFields();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while saving the order.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        cakeComboBox.getSelectionModel().clearSelection();
        smallRadio.setSelected(true);
        deliveryCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
