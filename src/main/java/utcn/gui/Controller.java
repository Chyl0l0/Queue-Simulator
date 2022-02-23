package utcn.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.stage.Stage;
import utcn.datamodels.Server;
import utcn.bussineslogic.SimulationManager;
import utcn.datamodels.Task;

import java.util.List;

public class Controller {
    private InputPage inputPage;
    private QueueSimulation queueSimulation;
    private SimulationManager simulationManager;
    private Stage stage;
    public Controller(InputPage inputPage, Stage stage) {
        this.inputPage = inputPage;
        this.stage=stage;
        this.inputPage.addRunHandler(new ButtonHandler());
    }
    class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            simulationManager=new SimulationManager(inputPage.getTimeLimit(), inputPage.getMaxProcessingTime(), inputPage.getMinProcessingTime(),
                    inputPage.getMaxArrivalTime(), inputPage.getMinArrivalTime(), inputPage.getNumberOfServers(), inputPage.getNumberOfClients());
            queueSimulation= new QueueSimulation(simulationManager);
            queueSimulation.setServersNumber(inputPage.getNumberOfServers());
            queueSimulation.displayServers();
            Scene scene = new Scene(queueSimulation.getRoot(),650,640);
            stage.setScene(scene);
            Thread thread=new Thread(simulationManager);
            thread.start();
            queueSimulation.startTimeline();

        }
    }


}
