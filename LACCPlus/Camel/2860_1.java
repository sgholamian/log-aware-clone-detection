//,temp,UnitOfWorkHelper.java,131,153,temp,UnitOfWorkHelper.java,107,129
//,2
public class xxx {
    public static void afterRouteSynchronizations(
            Route route, Exchange exchange, List<Synchronization> synchronizations, Logger log) {
        // work on a copy of the list to avoid any modification which may cause ConcurrentModificationException
        List<Synchronization> copy = new ArrayList<>(synchronizations);

        // reverse so we invoke it FILO style instead of FIFO
        Collections.reverse(copy);
        // and honor if any was ordered by sorting it accordingly
        copy.sort(OrderedComparator.get());

        // invoke synchronization callbacks
        for (Synchronization synchronization : copy) {
            if (synchronization instanceof SynchronizationRouteAware) {
                try {
                    log.trace("Invoking synchronization.onAfterRoute: {} with {}", synchronization, exchange);
                    ((SynchronizationRouteAware) synchronization).onAfterRoute(route, exchange);
                } catch (Throwable e) {
                    // must catch exceptions to ensure all synchronizations have a chance to run
                    log.warn("Exception occurred during onAfterRoute. This exception will be ignored.", e);
                }
            }
        }
    }

};