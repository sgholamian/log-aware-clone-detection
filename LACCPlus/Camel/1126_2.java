//,temp,ManagedRedeliverTest.java,81,100,temp,ManagedRedeliverRouteOnlyTest.java,57,78
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                context.getManagementStrategy().getManagementAgent().setStatisticsLevel(ManagementStatisticsLevel.RoutesOnly);

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
    }

};