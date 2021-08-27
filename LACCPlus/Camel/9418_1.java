//,temp,AsyncProcessorAwaitManagerInterruptTest.java,65,86,temp,AsyncProcessorAwaitManagerTest.java,59,83
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                context.addComponent("async", new MyAsyncComponent());

                from("direct:start").routeId("myRoute").to("mock:before").to("async:bye:camel?delay=2000").id("myAsync")
                        .to("mock:after").process(new Processor() {
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
                        }).transform(constant("Hi Camel")).to("mock:result");
            }

};