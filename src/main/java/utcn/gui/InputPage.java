package utcn.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InputPage {
    private final static int SIZE = 20;
    private TextField fieldTimeLimit = new TextField("20");
    private TextField fieldMaxProcessingTime = new TextField("4");
    private TextField fieldMinProcessingTime = new TextField("2");
    private TextField fieldMaxArrivalTime = new TextField("5");
    private TextField fieldMinArrivalTime = new TextField("2");
    private TextField fieldNumberOfServers = new TextField("2");
    private TextField fieldNumberOfClients = new TextField("4");
    private Button buttonRun = new Button("Run");
    private VBox root;

    public InputPage() {

        root = new VBox(20);
        root.setStyle("-fx-background-color: #292A2D");

        GridPane gridPane = new GridPane();
        root.setPadding(new Insets(30));
        //root.getChildren().add(gridPane);
        gridPane.setHgap(25);
        gridPane.setVgap(35);

        Text timeLimit = new Text("Time Limit");
        style(timeLimit);
        gridPane.add(timeLimit, 0, 0);
        style(fieldTimeLimit);
        gridPane.add(fieldTimeLimit, 1, 0);


        Text maxProcessingTime = new Text("Max Processing Time");
        style(maxProcessingTime);
        gridPane.add(maxProcessingTime, 0, 1);
        style(fieldMaxProcessingTime);
        gridPane.add(fieldMaxProcessingTime, 1, 1);

        Text minProcessingTime = new Text("Min Processing Time");
        style(minProcessingTime);
        gridPane.add(minProcessingTime, 0, 2);
        style(fieldMinProcessingTime);
        gridPane.add(fieldMinProcessingTime, 1, 2);

        Text maxArrivalTime = new Text("Max Arrival Time ");
        style(maxArrivalTime);
        gridPane.add(maxArrivalTime, 0, 3);
        style(fieldMaxArrivalTime);
        gridPane.add(fieldMaxArrivalTime, 1, 3);

        Text minArrivalTime = new Text("Min Arrival Time ");
        style(minArrivalTime);
        gridPane.add(minArrivalTime, 0, 4);
        style(fieldMinArrivalTime);
        gridPane.add(fieldMinArrivalTime, 1, 4);

        Text numberOfServers = new Text("Number Of Servers ");
        style(numberOfServers);
        gridPane.add(numberOfServers, 0, 5);
        style(fieldNumberOfServers);
        gridPane.add(fieldNumberOfServers, 1, 5);

        Text numberOfClients = new Text("Number Of Clients ");
        style(numberOfClients);
        gridPane.add(numberOfClients, 0, 6);
        style(fieldNumberOfClients);
        gridPane.add(fieldNumberOfClients, 1, 6);


        style(buttonRun);
        root.getChildren().addAll(gridPane,buttonRun);
        root.setAlignment(Pos.CENTER);

    }

    private void style(Text text) {
        text.setFont(new Font(SIZE));
        text.setFill(Color.web("#CCCCCC"));
        text.setTextAlignment(TextAlignment.CENTER);
    }

    private void style(TextField field) {
        field.setPrefWidth(500);
        field.setPrefHeight(30);
        field.setStyle("-fx-background-color: #6e7179; -fx-text-fill: #ffffff");
        field.setFont(new Font(SIZE));

    }

    private void style(Button button) {
        button.setStyle("-fx-background-color: #53585c; -fx-text-fill: #ffffff ");
        button.setFont(new Font(30));
        button.setOnMouseEntered(mouseEvent -> button.setStyle("-fx-background-color: #5AAEEE ; -fx-text-fill: #ffffff"));
        button.setOnMouseExited(mouseEvent -> button.setStyle("-fx-background-color: #53585c ; -fx-text-fill: #ffffff"));
        button.setPrefWidth(135);
        button.setPrefHeight(55);
    }


    public void addRunHandler(EventHandler<ActionEvent> eventHandler) {
        buttonRun.setOnAction(eventHandler);
    }

    public int getTimeLimit() {
        return Integer.parseInt( fieldTimeLimit.getText());
    }

    public int getMaxArrivalTime() {
        return Integer.parseInt( fieldMaxArrivalTime.getText());
    }

    public int getMinArrivalTime() {
        return Integer.parseInt( fieldMinArrivalTime.getText());
    }

    public int getMaxProcessingTime() {
        return Integer.parseInt( fieldMaxProcessingTime.getText());
    }

    public int getMinProcessingTime() {
        return Integer.parseInt( fieldMinArrivalTime.getText());
    }

    public int getNumberOfClients() {
        return Integer.parseInt( fieldNumberOfClients.getText());
    }

    public int getNumberOfServers() {
        return Integer.parseInt( fieldNumberOfServers.getText());
    }
    public VBox getRoot() {
        return root;
    }
}

