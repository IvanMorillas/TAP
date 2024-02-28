package Controller;
import Actions.*;
import Invokers.Invoker;
import Observer.Metrics;
import Observer.Observer;
import PolicyManager.PolicyManager;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller implements Observer {
    private List<Invoker> invokersList;
    private final int maxInvokers;
    private final Map<String, Action<?, ?>> actionsList = new HashMap<>();
    private final PolicyManager policy;
    private final List<Metrics> metricsList = new ArrayList<>();
    /**
     * Creates a new `Controller` instance with the specified maximum number of invokers and policy manager.
     *
     * @param maxInvokers Maximum number of invokers to manage
     * @param policy Policy manager to use for load balancing
     */
    public Controller(int maxInvokers, PolicyManager policy){
        this.policy = policy;
        this.maxInvokers = maxInvokers;
        this.invokersList = new ArrayList<>(maxInvokers);
    }
    /**
     * Registers a list of invokers with the controller.
     *
     * @param invokersList List of invokers to register
     */
    public void registerInvoker(List<Invoker> invokersList){
        this.invokersList = invokersList;
        //System.out.println("Invokers registered");
    }
    /**
     * Registers an action with the controller based on the specified ID, function, and memory requirements.
     *
     * @param id ID of the action
     * @param function Function to be executed by the action
     * @param memory Memory required by the action
     * @throws Exception if the action could not be registered
     */
    public <T, R> void registerAction(String id, Function<T, R> function, int memory) throws Exception {
        actionsList.put(id, new FunctionAction<>(function,memory));
        //System.out.println("Action "+ id +" Registered");
    }
    /**
     * Invokes an action based on the specified ID and input data.
     *
     * @param id ID of the action to invoke
     * @param input Input data for the action
     * @return Result of the action execution
     * @throws Exception if the action could not be invoked
     */
    public <T, R> Object invoke(String id, Object input) throws Exception {
        Action<T, R> executeAction = (Action<T, R>) actionsList.get(id);
        Invoker invoker = policy.selectInvoker(this, executeAction);
        Object result = invoker.invokeAction(executeAction, input);
        //System.out.println("Action "+ id +" invoked in " + invoker.toString());
        return result;
    }
    /**
     * Invokes an action asynchronously based on the specified ID and input data.
     *
     * @param id ID of the action to invoke
     * @param input Input data for the action
     * @return Future object for the action execution result
     * @throws Exception if the action could not be invoked
     */
    public <T, R>Future<Object> invokeAsync(String id, Object input) throws Exception{
        Action<T, R> executeAction = (Action<T, R>) actionsList.get(id);
        Invoker invoker = policy.selectInvoker(this, executeAction);
        return invoker.invokeActionAsync(executeAction, input);
    }
    /**
     * Returns the list of invokers managed by the controller.
     *
     * @return List of invokers
     */
    public List<Invoker> getInvokers() {
        return invokersList;
    }
    /**
     * Returns a string representation of the controller, including the list of invokers and actions.
     *
     * @return String representation of the controller
     */
    public String toString() {
        return "Controller{" + "invokers=" + invokersList + ", policyManager=" + policy + ", actions=" + actionsList + "}";
    }
    /**
     * Updates the controller's metrics list with the specified metric.
     *
     * @param metric Metrics object to update with
     */
    public void updateMetrics(Metrics metric){
        metricsList.add(metric);
        //System.out.println("Metric received: " + metric);
    }
    /**
     * Displays the list of metrics for the controller.
     */
    public void showMetrics(){
        System.out.println(metricsList.toString());
    }
    /**
     * Analyzes the controller's metrics and displays the metrics.
     *
     * The metrics are:
     *
     * * Average time
     * * Maximum time
     * * Minimum time
     * * Total time
     * * Memory invoker
     */
    public void analyzeMetrics() {
        System.out.println("\nMetrics:");
        // Calculate and display the average execution time of all actions
        double averageTime = metricsList.stream().mapToDouble(Metrics::getExecutionTime).average().orElse(0.0);
        System.out.println("\tAverage time: " + averageTime + " ms");
        // Calculates and displays the maximum and minimum execution time of all actions
        long maximumTime = metricsList.stream().mapToLong(Metrics::getExecutionTime).max().orElse(0L);
        System.out.println("\tMaximum time: " + maximumTime + " ms");
        long minimumTime = metricsList.stream().mapToLong(Metrics::getExecutionTime).min().orElse(0L);
        System.out.println("\tMinimum time: " + minimumTime + " ms");
        // Calculates and displays the total execution time of all actions
        long totalTime = metricsList.stream().mapToLong(Metrics::getExecutionTime).sum();
        System.out.println("\tTotal time: " + totalTime + " ms");
        // Calculates and displays the total memory utilization of each Invoker
        Map<Integer, Integer> memoryInvoker = metricsList.stream().collect(Collectors.groupingBy(Metrics::getId,Collectors.summingInt(Metrics::getUsedMemory)));
        memoryInvoker.forEach((invokerId, avgMemoryUsage) -> System.out.println("\tInvoker: " + invokerId + " -> Total memory: " + avgMemoryUsage + " MB"));
    }
}
