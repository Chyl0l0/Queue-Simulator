package utcn.bussineslogic;

import utcn.datamodels.Server;
import utcn.datamodels.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class SimulationManager implements Runnable {
    private int timeLimit = 30;
    private int maxProcessingTime = 4;
    private int minProcessingTime = 2;
    private int maxArrivalTime = 7;
    private int minArrivalTime = 2;
    private int numberOfServers = 2;
    private int numberOfClients = 4;
    private int currentTime=0;
    FileWriter fileWriter = null;
    private int peakHour=0;
    private int maxCurrentClients=0;

    private Scheduler scheduler;

    private List<Task> generatedTasks;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime, int numberOfServers, int numberOfClients) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        generatedTasks = Collections.synchronizedList(new ArrayList<Task>());
        generateNRandomTasks();
    }

    public SimulationManager() {
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        generatedTasks = Collections.synchronizedList(new ArrayList<Task>());
        generateNRandomTasks();
    }
    private void generateNRandomTasks() {
        Random random = new Random();
            for (int i = 0; i < numberOfClients; i++) {
                int processingTime = random.nextInt((maxProcessingTime + 1) - minProcessingTime) + minProcessingTime;
                int arrivalTime = random.nextInt((maxArrivalTime + 1) - minArrivalTime) + minArrivalTime;
                generatedTasks.add(new Task(i + 1, arrivalTime, processingTime));
            }
            Collections.sort(generatedTasks);
            try {
                fileWriter = new FileWriter("log.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void run() {
        currentTime = 0;
        while (currentTime < timeLimit) {
            if (isDone()) { break;}
            Iterator<Task> iterator=generatedTasks.iterator();
            while (iterator.hasNext()){
                Task task=iterator.next();
                if (task.getArrivalTime()<=currentTime){
                    scheduler.dispatchTask(task);
                    iterator.remove();
                }
                else break;
            }
            for (Server server: scheduler.getServers()){
                System.out.println(server.getWaitingPeriod());
            }
            printToSTDOUT(currentTime);
            printToFile(currentTime, fileWriter);
            calculatePeakHour(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       endSimulation();
    }

    private void endSimulation(){
        printStats();
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void printStats(){
        try {
            fileWriter.write("---------------------------\n");
            fileWriter.write("Average service time : "+ scheduler.getAverageServiceTime()+"\n");
            fileWriter.write("Average waiting time : "+ scheduler.getAverageWaitingTime()+"\n");
            fileWriter.write("Peak hour : "+ peakHour+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void calculatePeakHour(int hour){
        int clientsInQueues=0;
        for(Server server: scheduler.getServers()){
            clientsInQueues+=server.getSize();
        }
        if (clientsInQueues>maxCurrentClients){
            peakHour=hour;
            maxCurrentClients=clientsInQueues;
        }
    }
    private void printToSTDOUT(int currentTime) {
        System.out.print("Waiting clients: ");
        for (Task task : generatedTasks) {
            System.out.print(task + "; ");
        }
        System.out.println("\nTime " + currentTime);
        for (int i = 0; i < numberOfServers; i++) {
            Server server = scheduler.getServers().get(i);
            System.out.print("Queue " + (i + 1) + ": ");
            if (server.getSize() != 0) {
                System.out.println(server);
            } else {
                System.out.println("closed");
            }
        }
        System.out.println();
    }

    private boolean isDone() {
        if (generatedTasks.isEmpty()) {
            for (Server server : scheduler.getServers()) {
                if (server.getSize() > 0) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private void printToFile(int currentTime, FileWriter fileWriter) {
        try {
            fileWriter.write("Waiting clients: ");
            for (Task task : generatedTasks) {
                fileWriter.write(task + "; ");
            }
            fileWriter.write("\nTime " + currentTime + "\n");
            for (int i = 0; i < numberOfServers; i++) {
                Server server = scheduler.getServers().get(i);
                fileWriter.write("Queue " + (i + 1) + ": ");
                if (server.getSize() != 0) {
                    fileWriter.write(server + "\n");
                } else {
                    fileWriter.write("closed\n");
                }
            }
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getCurrentTime() {
        return currentTime;
    }

    public List<Task> getGeneratedTasks() {
        return generatedTasks;
    }
    public List<Server> getServers(){
        return scheduler.getServers();
    }
}
