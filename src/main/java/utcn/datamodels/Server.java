package utcn.datamodels;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable, Comparable<Server> {
    private ArrayBlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int maxTasksPerServer;

    public Server(int maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
        tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task task) {

        tasks.add(task);
        task.calculateFinishTime(waitingPeriod.get());
        waitingPeriod.addAndGet(task.getProcessingTime());
    }
    @Override
    public  void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                Task task = tasks.peek();
                if (task != null) {
                    task.decrementProcessingTime();
                    waitingPeriod.decrementAndGet();
                    if (task.getProcessingTime() == 0) {
                        tasks.take();
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public int getFreeSpacesNr() {
        return maxTasksPerServer - tasks.size();
    }
    public int getSize() {
        return tasks.size();
    }

    @Override
    public String toString() {
        StringBuilder res=new StringBuilder();
        for (Task task:tasks){
            res.append(task.toString()).append("; ");
        }
        return res.toString();
    }

    public ArrayBlockingQueue<Task> getTasks() {
        return tasks;
    }

    @Override
    public int compareTo(Server o) {
        return this.getWaitingPeriod()-o.getWaitingPeriod();
    }

}
