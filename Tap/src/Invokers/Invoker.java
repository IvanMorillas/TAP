package Invokers;

import Actions.Action;
import Observer.Observer;
import Observer.Observable;
import Observer.Metrics;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Invoker implements Observable {

    /**
     * Unique identifier for the invoker
     */
    private final int id;

    /**
     * Total available memory for the invoker
     */
    private final double totalMemory;

    /**
     *  Cumulative memory consumed by the invoker's currently executing actions
     */
    private int usedMemory = 0;

    /**
     * List of observers that will be notified when the invoker's metrics change
     */
    private final List<Observer> observerList = new ArrayList<>();

    /**
     * Thread pool for executing asynchronous action invocations
     */
    private final ExecutorService executorService;

    /**
     * Constructor for creating an invoker with the specified ID and total memory
     *
     * @param id Unique identifier for the invoker
     * @param totalMemory Total available memory for the invoker
     */
    public Invoker(int id, double totalMemory) {
        this.id = id;
        this.totalMemory = totalMemory;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    /**
     * Retrieves the invoker's ID
     *
     * @return Invoker's ID
     */
    public int getId(){return this.id;}

    /**
     * Checks whether the invoker has enough free memory to execute the specified action
     *
     * @param requiredMemory Memory requirement of the action to be executed
     * @return True if there is enough free memory, false otherwise
     */
    public boolean hasEnoughMemory(int requiredMemory){
        return (requiredMemory + usedMemory) <= totalMemory;
    }

    /**
     * Retrieves the currently used memory
     *
     * @return Currently used memory
     */
    public int getMemory() { return usedMemory; }

    /**
     * Invokes the specified action synchronously, returning the result
     *
     * @param action Action to be invoked
     * @param input Input data for the action
     * @return Result of the action execution
     * @throws Exception if the action could not be executed or if there is not enough memory
     */
    public <T, R> R invokeAction(Action<T, R> action, Object input) throws Exception {
        long start = System.currentTimeMillis();
        int memory = action.getMemory();
        R result;
        if ((usedMemory + memory) <= totalMemory) {
            usedMemory += memory;
            try{
                result = action.run((T) input);
            } catch (Exception e){
                throw new Exception("Error when executing this "+ action +" : "+ e.getMessage(), e);
            } finally {
                long end = System.currentTimeMillis();
                Metrics metrics = new Metrics(id, end-start, usedMemory);
                this.notify(metrics);
            }
        } else {
            throw new RuntimeException("Not enough resources for this action.");
        }
        return result;
    }

    /**
     * Invokes the specified action asynchronously, returning a future object to retrieve the result
     *
     * @param action Action to be invoked
     * @param input Input data for the action
     * @return Future object representing the asynchronous execution of the action
     * @throws Exception if the action could not be scheduled for execution or if there is not enough memory
     */
    public <T, R> Future<Object> invokeActionAsync(Action<T, R> action, Object input) throws Exception{
        long start = System.currentTimeMillis();
        int memory = action.getMemory();
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if ((usedMemory + memory) <= totalMemory) {
                usedMemory += memory;
                return executorService.submit(() -> {
                    try {
                        Thread.sleep(1000);
                        return action.run((T)input);
                    } finally {
                        long end = System.currentTimeMillis();
                        Metrics metrics = new Metrics(id, end-start, usedMemory);
                        this.notify(metrics);
                    }
                });
            } else {
                throw new RuntimeException("Not enough resources for this action.");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a string representation of the invoker, including its ID, total available memory, and currently used memory.
     *
     * @return A string representation of the invoker
     */
    public String toString() {
        return "Invoker{" + "id=" + id + ", totalMemoryMB=" + totalMemory + ", usedMemoryMB=" + usedMemory + "}";
    }

    /**
     * Adds the specified observer to the list of observers for this invoker.
     *
     * @param observer The observer to add
     */
    @Override
    public void subscribe(Observer observer){
        observerList.add(observer);
        System.out.println("Invoker "+ this.id +" subscribed");
    }

    /**
     * Removes the specified observer from the list of observers for this invoker.
     *
     * @param observer The observer to remove
     */
    @Override
    public void unsubscribe(Observer observer){
        observerList.remove(observer);
        System.out.println("Invoker "+ this.id +" unsubscribed");
    }

    /**
     * Notifies all registered observers about the updated metrics for this invoker.
     *
     * @param metric The updated metrics object
     */
    @Override
    public void notify(Metrics metric){
        Lock lock = new ReentrantLock();
        lock.lock();
        try{
            for(Observer observer : observerList){
                observer.updateMetrics(metric);
            }
        } finally {
            lock.unlock();
        }
        //System.out.println("Invoker "+ this.id +" notified");
    }
}