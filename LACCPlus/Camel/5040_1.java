//,temp,AbstractInfinispanEmbeddedClusteredTest.java,37,70,temp,AbstractInfinispanRemoteClusteredIT.java,77,107
//,3
public class xxx {
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    @Test
    public void test() throws Exception {
        final Logger logger = LoggerFactory.getLogger(getClass());
        final List<String> clients = IntStream.range(0, 3).mapToObj(Integer::toString).collect(Collectors.toList());
        final List<String> results = new ArrayList<>();
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(clients.size() * 2);
        final CountDownLatch latch = new CountDownLatch(clients.size());
        final String viewName = "myView";

        try (DefaultCacheManager cacheContainer = new DefaultCacheManager()) {
            InfinispanEmbeddedClusteredTestSupport.createCache(cacheContainer, viewName);

            for (String id : clients) {
                scheduler.submit(() -> {
                    try {
                        run(cacheContainer, viewName, id);
                        logger.debug("Node {} is shutting down", id);
                        results.add(id);
                    } catch (Exception e) {
                        logger.warn("", e);
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
    }

};