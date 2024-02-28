package PolicyManager;

import Actions.Action;
import Invokers.Invoker;
import Controller.Controller;

import java.util.List;

/**
 * A policy manager that selects the invoker with the least load among those that have enough memory to execute the action.
 */
public class UniformGroup extends PolicyManager {

    /**
     * This method selects an invoker from the list of available invokers provided by the controller, and returns it if it has enough memory to execute the specified action. If no invoker has enough memory, a `RuntimeException` is thrown.
     *
     * This policy manager selects the invoker with the least load among those that have enough memory to execute the action. This is a fair and efficient way to distribute load across multiple invokers.
     *
     * @param controller The controller that contains the list of available invokers
     * @param action The action that needs to be executed
     * @return The selected invoker, or `null` if no invoker has enough memory
     */
    @Override
    public Invoker selectInvoker(Controller controller, Action<?, ?> action) {
        int memory = action.getMemory();
        List<Invoker> invokers = controller.getInvokers();
        Invoker selectedInvoker = null;
        int minimum = Integer.MAX_VALUE;
        for (Invoker invoker : invokers) {
            int currentMemory = invoker.getMemory();
            if (currentMemory < minimum && invoker.hasEnoughMemory(memory)) {
                minimum = currentMemory;
                selectedInvoker = invoker;
            }
        }
        if (selectedInvoker == null) {
            throw new RuntimeException("Any invoker has available resources.");
        }
        System.out.println("InvokerSelect: " + selectedInvoker.toString());
        return selectedInvoker;
    }
}