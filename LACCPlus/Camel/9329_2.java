//,temp,UnitOfWorkSynchronizationAdapterTest.java,29,60,temp,UnitOfWorkTest.java,98,117
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("seda:async").to("direct:foo");
                from("direct:foo").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        log.info("Received: " + exchange);
                        exchange.getUnitOfWork().addSynchronization(synchronization);

                        String name = getName();
                        if (name.equals("testException")) {
                            log.info("Throwing exception!");
                            throw new Exception("Failing test!");
                        }
                    }
                });
            }
        };
    }

};