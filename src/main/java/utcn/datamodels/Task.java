package utcn.datamodels;

public class Task implements Comparable<Task> {
    private int id;
    private int arrivalTime;
    private int processingTime;
    private int finishTime;

    public Task(int id,int arrivalTime, int processingTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getFinishTime() {
        return finishTime;
    }
    public void decrementProcessingTime(){
        if (processingTime>0) {
            processingTime--;
        }
    }

    public void calculateFinishTime(int waitingPeriod)
    {
        finishTime=arrivalTime+processingTime+waitingPeriod;
    }

    @Override
    public int compareTo(Task o) {
        return this.arrivalTime-o.getArrivalTime();
    }

    @Override
    public String toString() {
        return "(" +id+","+arrivalTime+","+processingTime+")";
    }
}
