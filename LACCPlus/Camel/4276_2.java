//,temp,AsyncProcessorAwaitManagerInterruptWithRedeliveryTest.java,83,104,temp,AsyncProcessorAwaitManagerInterruptTest.java,71,84
//,3
public class xxx {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                int size = context.adapt(ExtendedCamelContext.class).getAsyncProcessorAwaitManager().size();
                                log.info("async inflight: {}", size);
                                assertEquals(1, size);

                                Collection<AsyncProcessorAwaitManager.AwaitThread> threads
                                        = context.adapt(ExtendedCamelContext.class).getAsyncProcessorAwaitManager().browse();
                                AsyncProcessorAwaitManager.AwaitThread thread = threads.iterator().next();

                                // lets interrupt it
                                String id = thread.getExchange().getExchangeId();
                                context.adapt(ExtendedCamelContext.class).getAsyncProcessorAwaitManager().interrupt(id);
                            }

};