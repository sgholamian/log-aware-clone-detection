//,temp,AbstractInfinispanEmbeddedClusteredTest.java,37,70,temp,AbstractInfinispanRemoteClusteredIT.java,77,107
//,3
public class xxx {
    public void runTest(Function<RunnerEnv, RouteBuilder> routeBuilderFunction) throws Exception {
        final List<String> clients = IntStream.range(0, 3).mapToObj(Integer::toString).collect(Collectors.toList());
        final List<String> results = new ArrayList<>();

        final CountDownLatch latch = new CountDownLatch(clients.size());
        final Logger logger = LoggerFactory.getLogger(getClass());

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(clients.size() * 2);

        for (String id : clients) {

            scheduler.submit(() -> {
                try {
                    run(id, routeBuilderFunction);

                    logger.debug("Node {} is shutting down", id);
                    results.add(id);
                } catch (Exception e) {
                    logger.warn("Failed to run job: {}", e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        scheduler.shutdownNow();

        assertThat(results).hasSameSizeAs(clients);
        assertThat(results).containsAll(clients);
    }

};