//,temp,ConsulClusteredRoutePolicyIT.java,71,111,temp,JGroupsLockMasterTest.java,66,104
//,3
public class xxx {
    private static void run(String id) {
        try {
            int events = ThreadLocalRandom.current().nextInt(2, 6);
            CountDownLatch contextLatch = new CountDownLatch(events);

            ConsulClusterService consulClusterService = new ConsulClusterService();
            consulClusterService.setId("node-" + id);
            consulClusterService.setUrl(service.getConsulUrl());

            LOGGER.info("Consul URL {}", consulClusterService.getUrl());

            DefaultCamelContext context = new DefaultCamelContext();
            context.disableJMX();
            context.setName("context-" + id);
            context.addService(consulClusterService);
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("timer:consul?delay=1000&period=1000").routeId("route-" + id)
                            .routePolicy(ClusteredRoutePolicy.forNamespace("my-ns")).log("From ${routeId}")
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