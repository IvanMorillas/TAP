package PolicyManager;

import Actions.Action;
import Controller.Controller;
import Invokers.Invoker;

import java.util.List;

/**
 * A policy manager that selects an invoker in a round-robin fashion, until
 * an invoker with enough memory is found or all invokers have been tried.
 */
public class RoundRobin extends PolicyManager{

    /**
     * The current index in the invoker list, used for round-robin selection.
     */
    private int index = 0;

    /**
     * This method selects an invoker from the list of available invokers provided by the controller, and returns it if it has enough memory to execute the specified action. If no invoker has enough memory, a `RuntimeException` is thrown.
     *
     * @param controller The controller that contains the list of available invokers
     * @param action The action that needs to be executed
     * @return The selected invoker, or `null` if no invoker has enough memory
     */
    @Override
    public Invoker selectInvoker(Controller controller, Action<?,?> action) {
        int memory = action.getMemory();
        List<Invoker> invokers = controller.getInvokers();
        int invokersTried = 0;
        while (invokersTried < invokers.size()) {
            Invoker selectedInvoker = invokers.get(index);
            index = (index + 1) % invokers.size();
            if (selectedInvoker.hasEnoughMemory(memory)) {
                System.out.println("InvokerSelect: " + selectedInvoker.toString());
                return selectedInvoker;
            }
            invokersTried++;
        }
        throw new RuntimeException("Any invoker has available resources.");
    }
}
