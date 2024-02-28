package PolicyManager;
import Actions.Action;
import Invokers.Invoker;
import Controller.Controller;

import java.util.List;

/**
 * An abstract class for selecting an invoker based on a specific policy.
 */
public abstract class PolicyManager {

    /**
     * Selects the most appropriate invoker for the specified action.
     *
     * @param controller The controller that contains the invokers
     * @param action The action to be executed
     * @return The selected invoker
     */
    public abstract Invoker selectInvoker(Controller controller, Action<?,?> action);
}
