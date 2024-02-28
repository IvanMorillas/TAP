package PolicyManager;

import Actions.Action;
import Invokers.Invoker;
import Controller.Controller;

import java.util.List;

/**
 * A policy manager that selects the first available invoker that can execute the action.
 */
public class GreedyGroup extends PolicyManager {

    /**
     * Selects the most appropriate invoker for the specified action, using a greedy approach.
     *
     * @param controller The controller that contains the invokers
     * @param action The action to be executed
     * @return The selected invoker
     */
    @Override
    public Invoker selectInvoker(Controller controller, Action<?, ?> action) {
        int memory = action.getMemory();
        Invoker selectedInvoker = null;
        List<Invoker> invokers = controller.getInvokers();
        for (Invoker invoker : invokers) {
            if (invoker.hasEnoughMemory(memory)) {
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