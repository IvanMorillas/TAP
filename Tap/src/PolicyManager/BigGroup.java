package PolicyManager;

import Actions.Action;
import Controller.Controller;
import Invokers.Invoker;

import java.util.List;

/**
 * A policy manager that first tries to select the invoker with the most available memory, and if none is found, it uses a round-robin approach.
 */
public class BigGroup extends PolicyManager {

    /**
     * The current index in the invoker list, used for round-robin selection.
     */
    private int index = 0;

    /**
     * Selects the most appropriate invoker for the specified action, using a big-group approach.
     *
     * @param controller The controller that contains the invokers
     * @param action The action to be executed
     * @return The selected invoker
     */
    public Invoker selectInvoker(Controller controller, Action<?, ?> action) {
        int memory = action.getMemory();
        int groupSize = 6; // Group size
        int numGroups = (int) Math.ceil((double) memory / groupSize); //Number of groups to distribute
        List<Invoker> invokers = controller.getInvokers();

        // Try to place packed groups in the same invoker
        Invoker bestPackagingInvoker = null;
        int maxPackagingCapacity = 0;
        for (Invoker invoker : invokers) {
            int availableMemory = invoker.getMemory();
            int possibleGroups = availableMemory / groupSize;
            if (possibleGroups >= numGroups && availableMemory > maxPackagingCapacity) {
                bestPackagingInvoker = invoker;
                maxPackagingCapacity = availableMemory;
            }
        }

        if (bestPackagingInvoker != null) {
            System.out.println("Packed groups on Invoker: " + bestPackagingInvoker.toString());
            return bestPackagingInvoker;
        }

        //If not all groups can be packed, look for invokers with enough space
        Invoker bestInvoker = null;
        int maxMemory = 0;
        for (Invoker invoker : invokers) {
            int availableMemory = invoker.getMemory();
            if (availableMemory >= memory && availableMemory > maxMemory) {
                bestInvoker = invoker;
                maxMemory = availableMemory;
            }
        }

        if (bestInvoker != null) {
            System.out.println("BestInvokerSelect: " + bestInvoker.toString());
            return bestInvoker;
        }

        //If no invoker has enough space, it tries to distribute the groups to available invokers
        invokers = controller.getInvokers();
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
        throw new RuntimeException("Any invoker has available resources for all groups.");
    }

}