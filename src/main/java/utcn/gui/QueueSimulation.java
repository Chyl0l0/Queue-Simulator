package utcn.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import utcn.datamodels.Server;
import utcn.bussineslogic.SimulationManager;
import utcn.datamodels.Task;

import java.util.ArrayList;
import java.util.List;


public class QueueSimulation {
    private VBox root;
    private ArrayList<Integer> servers;
    private ArrayList<HBox> rows;
    private List<Task> waitingClients;
    private List<Server> serversList;
    private FlowPane waintingList;
    private HBox simRow;
    private Timeline timeline;
    private int serversNumber=4;
    private Integer simulationTime=0;
    public QueueSimulation(SimulationManager simulationManager) {

        root = new VBox(20);
        root.setStyle("-fx-background-color: #292A2D");
        root.setPadding(new Insets(30));

        servers =new ArrayList<Integer>(serversNumber);
        rows =new ArrayList<HBox>(serversNumber);

        simRow=new HBox(10);;
        waintingList = new FlowPane(35,10);
        waintingList.setAlignment(Pos.CENTER_LEFT);
        waintingList.setStyle("-fx-background-color: #292A2D");
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                List<Task> waitingClients = simulationManager.getGeneratedTasks();
                                setSimulationTime(simulationManager.getCurrentTime());
                                setWaitingClients(simulationManager.getGeneratedTasks());
                                updateWaitingList();
                                setServersList(simulationManager.getServers());
                                updateServers();
                            }
                        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    public void displayServers(){
        for (int i=1;i<=serversNumber;i++){
            servers.add(i);
        }
        for( Integer integer:servers){
            Circle circle = new Circle(30);
            circle.setFill(Color.web("#5BB9E8"));
            Text text = new Text(integer.toString());
            text.setFont(new Font("Consolas",40));
            text.setBoundsType(TextBoundsType.VISUAL);
            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle,text);
            stack.setAlignment(Pos.CENTER);
            HBox row=new HBox(10);;
            row.getChildren().add(stack);
            row.setAlignment(Pos.CENTER_LEFT);
            rows.add(row);
            root.getChildren().add(row);
        }
        root.getChildren().add(simRow);
        root.getChildren().add(waintingList);
    }
    public void displaySimulationTime(){
        Circle circle = new Circle(30);
        circle.setFill(Color.web("#3bff6f"));
        Text text = new Text(simulationTime.toString());
        text.setFont(new Font("Consolas",30));
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle,text);
        stack.setAlignment(Pos.CENTER);
        simRow.getChildren().add(stack);
        simRow.setAlignment(Pos.CENTER);
    }
    public VBox getRoot() {
        return root;
    }
    public class Client extends Label {
        private int id;
        private int arrivalTime;
        private int processingTime;

        public Client(int id, int arrivalTime, int processingTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.processingTime = processingTime;
            setText(id+"|"+arrivalTime+"|"+processingTime);
            setFont(new Font("Consolas",30));
            setPrefHeight(45);
            setPrefWidth(120);
            setAlignment(Pos.CENTER);
            setStyle("-fx-background-radius: 10 ;-fx-background-color: #53585c; -fx-text-fill: #ffffff ");
        }
    }

    public void setWaitingClients(List<Task> waitingClients) {
        this.waitingClients = waitingClients;
    }

    public void setServersList(List<Server> serversList) {
        this.serversList = serversList;
    }

    public void updateServers(){
        int k=0;
        for (Server server: serversList){
            while(rows.get(k).getChildren().size()>1){
                rows.get(k).getChildren().remove(1);
            }
            for (Task task: server.getTasks()){
                rows.get(k).getChildren().add(new Client(task.getId(), task.getArrivalTime(), task.getProcessingTime()));
            }
            k++;
        }
    }
    public void updateWaitingList(){
        simRow.getChildren().clear();
        displaySimulationTime();
        waintingList.getChildren().clear();
        for (Task task:waitingClients){
            waintingList.getChildren().add(new Client(task.getId(),task.getArrivalTime(),task.getProcessingTime()));
        }
    }

    public void startTimeline(){
        timeline.play();
    }

    public void setServersNumber(int serversNumber) {
        this.serversNumber = serversNumber;
    }

    public void setSimulationTime(Integer simulationTime) {
        this.simulationTime = simulationTime-1;
    }
}
