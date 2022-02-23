package utcn.bussineslogic;

import utcn.datamodels.Server;
import utcn.datamodels.Task;

import java.util.*;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private int waitingTimeSum;
    private int serviceTimeSum;
    private int tasksDispatched;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = Collections.synchronizedList(new ArrayList<Server>());
        for (int i = 0; i < maxNoServers; i++) {
            servers.add(new Server(maxTasksPerServer));
            Thread thread = new Thread(servers.get(i));
            thread.start();
        }
    }
    public  void dispatchTask(Task task) {
        Optional<Server> minimumWaitingTimeServer = servers.stream()
                .filter(server -> server.getFreeSpacesNr() != 0)
                .min(Comparator.comparing(Server::getWaitingPeriod));
        if (minimumWaitingTimeServer.isPresent()) {
            waitingTimeSum += minimumWaitingTimeServer.get().getWaitingPeriod();
            serviceTimeSum += task.getProcessingTime();
            minimumWaitingTimeServer.get().addTask(task);
            tasksDispatched++;
        }
    }
    public List<Server> getServers() {
        return servers;
    }

    public double getAverageWaitingTime() {
        return (double) waitingTimeSum / tasksDispatched;
    }

    public double getAverageServiceTime() {
        return (double) serviceTimeSum / tasksDispatched;
    }

}
