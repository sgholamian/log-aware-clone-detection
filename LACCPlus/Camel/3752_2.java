//,temp,MainDurationEventNotifier.java,136,163,temp,MainDurationEventNotifier.java,61,87
//,3
public class xxx {
    @Override
    public void notify(CamelEvent event) throws Exception {
        // ignore any event that is received if shutdown is in process
        if (!shutdownStrategy.isRunAllowed()) {
            return;
        }

        boolean begin = event instanceof ExchangeCreatedEvent;
        boolean complete = event instanceof ExchangeCompletedEvent || event instanceof ExchangeFailedEvent;

        if (maxMessages > 0 && complete) {
            boolean result = doneMessages.incrementAndGet() >= maxMessages;
            LOG.trace("Duration max messages check {} >= {} -> {}", doneMessages.get(), maxMessages, result);

            if (result && shutdownStrategy.isRunAllowed()) {
                LOG.info("Duration max messages triggering shutdown of the JVM.");
                // use thread to stop Camel as otherwise we would block current thread
                camelContext.getExecutorServiceManager().newThread("CamelMainShutdownCamelContext", this::shutdownTask).start();
            }
        }

        // idle reacts on both incoming and complete messages
        if (maxIdleSeconds > 0 && (begin || complete)) {
            LOG.trace("Message activity so restarting stop watch");
            watch.restart();
        }
    }

};