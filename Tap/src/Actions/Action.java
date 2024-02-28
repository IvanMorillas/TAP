package Actions;

/**
 * Interface to execute actions as services.
 * @param <T>
 * @param <R>
 */
public interface Action<T, R> {
    /**
     * Main method of action.
     * @param args Receives an input argument of type `T`.
     * @return Returns an output value of type `R`.
     * @throws Exception It can throw an exception if any error occurs during the execution of the action.
     */
    R run(T args) throws Exception;

    /**
     * Returns the amount of memory the action needs to execute.
     * @return
     */
    int getMemory();
}
