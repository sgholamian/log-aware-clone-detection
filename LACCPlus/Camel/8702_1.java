//,temp,MultiQueueWithTopicThreadPoolTest.java,135,153,temp,MultiQueueWithTopicThreadPoolTest.java,55,70
//,3
public class xxx {
    @Test
    public void multiThreadPoolSizeExceeded() throws InterruptedException {
        assertThrows(IllegalStateException.class, () -> {
            LinkedBlockingQueue<Integer> finalResultsOrder = new LinkedBlockingQueue<>();
            int capacity = 3;
            int poolSize = 3;
            MultiQueueWithTopicThreadPool pool = new MultiQueueWithTopicThreadPool(poolSize, capacity, "test");
            for (int i = 0; i < (capacity + 1) * poolSize + 1; i++) {
                pool.execute(i % poolSize, () -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        LOG.warn("Interrupted while running the test", e);
                    }
                });
            }
            Thread.sleep(100);
        });
    }

};