package utcn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utcn.bussineslogic.SimulationManager;
import utcn.gui.Controller;
import utcn.gui.InputPage;



/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        InputPage inputPage=new InputPage();
        stage.setTitle("Queue Simulator");
        Scene scene = new Scene(inputPage.getRoot(),650,640);
        stage.setScene(scene);
        stage.show();

        Controller controller=new Controller(inputPage,stage);


    }

    public static void main(String[] args) {
        launch();
        /*
        SimulationManager simulationManager= new SimulationManager();
        Thread thread=new Thread(simulationManager);
        thread.start();
         */
    }

}