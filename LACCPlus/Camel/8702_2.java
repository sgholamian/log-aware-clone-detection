//,temp,MultiQueueWithTopicThreadPoolTest.java,135,153,temp,MultiQueueWithTopicThreadPoolTest.java,55,70
//,3
public class xxx {
    @Test
    public void singleThreadPoolSizeExceeded() {
        assertThrows(IllegalStateException.class, () -> {
            int capacity = 10;
            MultiQueueWithTopicThreadPool pool = new MultiQueueWithTopicThreadPool(1, capacity, "test");
            for (int i = 0; i < capacity + 2; i++) {
                pool.execute(i % 3, () -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        LOG.warn("Interrupted while running the test", e);
                    }
                });
            }
        });
    }

};