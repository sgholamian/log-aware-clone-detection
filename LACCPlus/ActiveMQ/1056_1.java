//,temp,PListTestSupport.java,339,400,temp,PListTest.java,325,389
//,3
public class xxx {
    @Test
    public void testConcurrentAddRemoveWithPreload() throws Exception {
        File directory = store.getDirectory();
        store.stop();
        IOHelper.mkdirs(directory);
        IOHelper.deleteChildren(directory);
        store = createConcurrentAddRemoveWithPreloadPListStore();
        store.setDirectory(directory);
        store.start();

        final int iterations = 500;
        final int numLists = 10;

        // prime the store

        // create/delete
        LOG.info("create");
        for (int i=0; i<numLists;i++) {
            new Job(i, PListTestSupport.TaskType.CREATE, iterations).run();
        }

        LOG.info("delete");
        for (int i=0; i<numLists;i++) {
            new Job(i, PListTestSupport.TaskType.DELETE, iterations).run();
        }

        LOG.info("fill");
        for (int i=0; i<numLists;i++) {
            new Job(i, PListTestSupport.TaskType.ADD, iterations).run();
        }
        LOG.info("remove");
        for (int i=0; i<numLists;i++) {
            new Job(i, PListTestSupport.TaskType.REMOVE, iterations).run();
        }

        LOG.info("check empty");
        for (int i=0; i<numLists;i++) {
            assertEquals("empty " + i, 0, store.getPList("List-" + i).size());
        }

        LOG.info("delete again");
        for (int i=0; i<numLists;i++) {
            new Job(i, PListTestSupport.TaskType.DELETE, iterations).run();
        }

        LOG.info("fill again");
        for (int i=0; i<numLists;i++) {
            new Job(i, PListTestSupport.TaskType.ADD, iterations).run();
        }

        LOG.info("parallel add and remove");
        executor = Executors.newFixedThreadPool(numLists*2);
        for (int i=0; i<numLists*2; i++) {
            executor.execute(new Job(i, i>=numLists ? PListTestSupport.TaskType.ADD : PListTestSupport.TaskType.REMOVE, iterations));
        }

        executor.shutdown();
        LOG.info("wait for parallel work to complete");
        boolean finishedInTime = executor.awaitTermination(60*5, TimeUnit.SECONDS);
        assertTrue("no exceptions", exceptions.isEmpty());
        assertTrue("finished ok", finishedInTime);
    }

};