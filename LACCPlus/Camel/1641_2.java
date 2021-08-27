//,temp,AtomixClientRoutePolicyTestSupport.java,84,119,temp,ZooKeeperMasterIT.java,74,114
//,3
public class xxx {
    private static void run(String connectString, String id) {
        try {
            int events = ThreadLocalRandom.current().nextInt(2, 6);
            CountDownLatch contextLatch = new CountDownLatch(events);

            ZooKeeperClusterService service = new ZooKeeperClusterService();
            service.setId("node-" + id);
            service.setNodes(connectString);
            service.setBasePath("/camel/master");

            DefaultCamelContext context = new DefaultCamelContext();
            context.disableJMX();
            context.setName("context-" + id);
            context.addService(service);
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("master:zk:timer:master?delay=1000&period=1000")
                            .routeId("route-" + id)
                            .log("From ${routeId}")
                            .process(e -> contextLatch.countDown());
                }
            });

            // Start the context after some random time so the startup order
            // changes for each test.
            Thread.sleep(ThreadLocalRandom.current().nextInt(500));
            context.start();

            contextLatch.await();

            LOGGER.debug("Shutting down node {}", id);
            RESULTS.add(id);

            context.stop();

            LATCH.countDown();
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
    }

};