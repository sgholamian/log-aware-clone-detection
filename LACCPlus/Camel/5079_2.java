//,temp,SimpleScheduledRoutePolicyTest.java,232,272,temp,SimpleScheduledRoutePolicyTest.java,187,228
//,3
public class xxx {
    @Test
    public void testScheduledSuspendAndResumeRoutePolicy() throws Exception {
        MockEndpoint success = context.getEndpoint("mock:success", MockEndpoint.class);
        success.expectedMessageCount(1);

        context.getComponent("direct", DirectComponent.class).setBlock(false);
        context.getComponent("quartz", QuartzComponent.class)
                .setPropertiesFile("org/apache/camel/routepolicy/quartz/myquartz.properties");
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                SimpleScheduledRoutePolicy policy = new SimpleScheduledRoutePolicy();
                long suspendTime = System.currentTimeMillis() + 1000L;
                policy.setRouteSuspendDate(new Date(suspendTime));
                policy.setRouteSuspendRepeatCount(0);
                policy.setRouteSuspendRepeatInterval(3000);
                long resumeTime = System.currentTimeMillis() + 4000L;
                policy.setRouteResumeDate(new Date(resumeTime));
                policy.setRouteResumeRepeatCount(1);
                policy.setRouteResumeRepeatInterval(3000);

                from("direct:start")
                        .routeId("test")
                        .routePolicy(policy)
                        .to("mock:success");
            }
        });
        context.start();
        Thread.sleep(1000);

        try {
            template.sendBody("direct:start", "Ready or not, Here, I come");
            fail("Should have thrown an exception");
        } catch (CamelExecutionException e) {
            LOG.debug("Consumer successfully suspended");
        }

        Thread.sleep(4000);
        template.sendBody("direct:start", "Ready or not, Here, I come");

        context.getComponent("quartz", QuartzComponent.class).stop();
        success.assertIsSatisfied();
    }

};