//,temp,UnitOfWorkSynchronizationAdapterTest.java,29,60,temp,UnitOfWorkTest.java,98,117
//,3
public class xxx {
            public void configure() {
                from("seda:async").to("direct:foo");
                from("direct:foo").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        log.info("Received: " + exchange);

                        exchange.getUnitOfWork().addSynchronization(new SynchronizationAdapter() {
                            @Override
                            public void onComplete(Exchange exchange) {
                                completed = exchange;
                                foo = exchange.getIn().getHeader("foo");
                                doneLatch.countDown();
                            }
                        });

                        exchange.getUnitOfWork().addSynchronization(new SynchronizationAdapter() {
                            @Override
                            public void onFailure(Exchange exchange) {
                                failed = exchange;
                                baz = exchange.getIn().getHeader("baz");
                                doneLatch.countDown();
                            }
                        });

                        String name = getName();
                        if (name.equals("testException")) {
                            log.info("Throwing exception!");
                            throw new Exception("Failing test!");
                        }
                    }
                });
            }

};