//,temp,AsyncProcessorAwaitManagerInterruptWithRedeliveryTest.java,83,104,temp,AsyncProcessorAwaitManagerInterruptTest.java,71,84
//,3
public class xxx {
    private void createThreadToInterrupt() {
        new Thread(() -> {
            // Allow some time for camel exchange to enter the re-deliveries
            try {
                latch.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOG.info("The test execution was interrupted", e);
            }

            // Get our blocked thread
            int size = context.adapt(ExtendedCamelContext.class).getAsyncProcessorAwaitManager().size();
            assertEquals(1, size);

            Collection<AsyncProcessorAwaitManager.AwaitThread> threads
                    = context.adapt(ExtendedCamelContext.class).getAsyncProcessorAwaitManager().browse();
            AsyncProcessorAwaitManager.AwaitThread thread = threads.iterator().next();

            // Interrupt it
            String id = thread.getExchange().getExchangeId();
            context.adapt(ExtendedCamelContext.class).getAsyncProcessorAwaitManager().interrupt(id);
        }).start();
    }

};