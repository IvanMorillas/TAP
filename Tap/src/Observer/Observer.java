package Observer;

/**
 * An interface for objects that want to be notified of changes to metrics.
 */
public interface Observer {

    /**
     * Notifies this observer about the updated metrics.
     *
     * @param metrics The updated metrics object
     */
    void updateMetrics(Metrics metrics);
}
