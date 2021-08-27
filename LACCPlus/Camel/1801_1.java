//,temp,AtomixRoutePolicyFactoryTest.java,75,116,temp,FileLockClusteredRoutePolicyTest.java,68,108
//,3
public class xxx {
    private void run(Address address) {
        try (DefaultCamelContext context = new DefaultCamelContext()) {
            int events = ThreadLocalRandom.current().nextInt(2, 6);
            CountDownLatch contextLatch = new CountDownLatch(events);

            AtomixClusterService service = new AtomixClusterService();
            service.setId("node-" + address.port());
            service.setStorageLevel(StorageLevel.MEMORY);
            service.setAddress(address);
            service.setNodes(addresses);

            context.disableJMX();
            context.setName("context-" + address.port());
            context.addService(service);
            context.addRoutePolicyFactory(ClusteredRoutePolicyFactory.forNamespace("my-ns"));
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("timer:atomix?delay=1000&period=1000")
                            .routeId("route-" + address.port())
                            .log("From ${routeId}")
                            .process(e -> contextLatch.countDown());
                }
            });

            // Start the context after some random time so the startup order
            // changes for each test.
            Thread.sleep(ThreadLocalRandom.current().nextInt(500));
            context.start();

            contextLatch.await();

            LOGGER.debug("Shutting down node {}", address);
            results.add(address);

            context.stop();

            latch.countDown();
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
    }

};