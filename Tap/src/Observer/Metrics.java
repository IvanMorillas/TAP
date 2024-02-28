package Observer;

/**
 * A class to represent metrics for a particular invoker.
 */
public class Metrics{

    /**
     * Unique identifier for the invoker
     */
    private final int id;

    /**
     * Execution time of the invoker's latest action
     */
    private final long executionTime;

    /**
     * Cumulative memory used by the invoker's latest action
     */
    private final int usedMemory;

    /**
     * Creates a new `Metrics` object with the specified ID, execution time, and used memory.
     *
     * @param id The ID of the invoker
     * @param time The execution time of the invoker's latest action
     * @param mem The cumulative memory used by the invoker's latest action
     */
    public Metrics(int id, long time, int mem){
        this.id = id;
        this.executionTime = time;
        this.usedMemory = mem;
    }

    /**
     * Retrieves the ID of the invoker
     *
     * @return The ID of the invoker
     */
    public int getId(){ return id;}

    /**
     * Retrieves the execution time of the invoker's latest action
     *
     * @return The execution time of the invoker's latest action, in milliseconds
     */
    public long getExecutionTime(){ return executionTime;}

    /**
     * Retrieves the cumulative memory used by the invoker's latest action
     *
     * @return The cumulative memory used by the invoker's latest action, in bytes
     */
    public int getUsedMemory(){ return usedMemory; }

    /**
     * Returns a string representation of the metrics
     *
     * @return A string representation of the metrics, including the invoker ID, execution time, and used memory
     */
    public String toString(){
        return "Metrics{" + "invokerId='" + id + '\'' + ", executionTime=" + executionTime + ", memoryUsage=" + usedMemory + "}\n";
    }
}
