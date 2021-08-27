//,temp,PListTestSupport.java,421,465,temp,PListTest.java,408,454
//,3
public class xxx {
    public void do_testConcurrentAddIterateRemove(boolean enablePageCache) throws Exception {
        File directory = store.getDirectory();
        store.stop();
        IOHelper.mkdirs(directory);
        IOHelper.deleteChildren(directory);
        store = new PListStoreImpl();
        store.setIndexEnablePageCaching(enablePageCache);
        store.setIndexPageSize(2 * 1024);
        store.setDirectory(directory);
        store.start();

        final int iterations = 500;
        final int numLists = 10;

        LOG.info("create");
        for (int i = 0; i < numLists; i++) {
            new Job(i, PListTest.TaskType.CREATE, iterations).run();
        }

        LOG.info("fill");
        for (int i = 0; i < numLists; i++) {
            new Job(i, PListTest.TaskType.ADD, iterations).run();
        }

        LOG.info("parallel add and remove");
        executor = Executors.newFixedThreadPool(400);
        final int numProducer = 5;
        final int numConsumer = 10;
        for (int i = 0; i < numLists; i++) {
            for (int j = 0; j < numProducer; j++) {
                executor.execute(new Job(i, PListTest.TaskType.ADD, iterations * 2));
            }
            for (int k = 0; k < numConsumer; k++) {
                executor.execute(new Job(i, TaskType.ITERATE_REMOVE, iterations / 4));
            }
        }

        for (int i = numLists; i < numLists * 10; i++) {
            executor.execute(new Job(i, PListTest.TaskType.ADD, iterations));
        }

        executor.shutdown();
        LOG.info("wait for parallel work to complete");
        boolean shutdown = executor.awaitTermination(60 * 60, TimeUnit.SECONDS);
        assertTrue("no exceptions: " + exceptions, exceptions.isEmpty());
        assertTrue("test did not  timeout ", shutdown);
    }

};