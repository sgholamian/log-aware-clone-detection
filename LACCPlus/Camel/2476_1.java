//,temp,AtomixClientRoutePolicyFactoryTestSupport.java,84,119,temp,FileLockClusteredRoutePolicyFactoryTest.java,68,108
//,3
public class xxx {
    private void run(String id) {
        try (DefaultCamelContext context = new DefaultCamelContext()) {
            int events = ThreadLocalRandom.current().nextInt(2, 6);
            CountDownLatch contextLatch = new CountDownLatch(events);

            context.disableJMX();
            context.setName("context-" + id);
            context.addService(createClusterService(id, address));
            context.addRoutePolicyFactory(ClusteredRoutePolicyFactory.forNamespace("my-ns"));
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("timer:atomix?delay=1000&period=1000")
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

            LOGGER.debug("Shutting down client node {}", id);
            results.add(id);

            context.stop();

            latch.countDown();
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
    }

};