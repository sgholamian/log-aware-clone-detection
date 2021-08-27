//,temp,ManagedRedeliverTest.java,84,98,temp,ManagedRedeliverRouteOnlyTest.java,60,76
//,3
public class xxx {
            @Override
            public void configure() throws Exception {
                onException(Exception.class).handled(true)
                        .redeliveryDelay(0)
                        .maximumRedeliveries(4).logStackTrace(false)
                        .setBody().constant("Error");

                from("direct:start")
                        .to("mock:foo")
                        .process(exchange -> {
                            log.info("Invoking me");

                            throw new IllegalArgumentException("Damn");
                        }).id("myprocessor");
            }

};