//,temp,MainDurationEventNotifier.java,136,163,temp,MainDurationEventNotifier.java,61,87
//,3
public class xxx {
    private void idleTask() {
        // don't run the task if shutdown is in process
        if (!shutdownStrategy.isRunAllowed()) {
            return;
        }

        if (watch == null) {
            // camel has not been started yet
            return;
        }

        // any inflight messages currently
        int inflight = camelContext.getInflightRepository().size();
        if (inflight > 0) {
            LOG.trace("Duration max idle check is skipped due {} inflight messages", inflight);
            return;
        }

        long seconds = watch.taken() / 1000;
        boolean result = seconds >= maxIdleSeconds;
        LOG.trace("Duration max idle check {} >= {} -> {}", seconds, maxIdleSeconds, result);

        if (result && shutdownStrategy.isRunAllowed()) {
            LOG.info("Duration max idle triggering shutdown of the JVM.");
            // use thread to stop Camel as otherwise we would block current thread
            camelContext.getExecutorServiceManager().newThread("CamelMainShutdownCamelContext", this::shutdownTask).start();
        }
    }

};