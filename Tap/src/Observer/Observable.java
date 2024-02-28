package Observer;

/**
 * An interface for objects that can notify other objects about changes to their state.
 */
public interface Observable {

    /**
     * Adds an observer to the list of observers to be notified about changes.
     *
     * @param o The observer to add
     */
    void subscribe(Observer o);

    /**
     * Removes an observer from the list of observers to be notified about changes.
     *
     * @param o The observer to remove
     */
    void unsubscribe(Observer o);

    /**
     * Notifies all registered observers about the latest state changes.
     */
    void notify(Metrics m);
}
