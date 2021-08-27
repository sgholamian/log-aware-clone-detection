//,temp,JobSchedulerStoreTest.java,36,76,temp,InMemoryJobSchedulerStoreTest.java,40,73
//,3
public class xxx {
    @Test(timeout = 120 * 1000)
    public void testRestart() throws Exception {
        JobSchedulerStore store = new JobSchedulerStoreImpl();
        File directory = new File("target/test/ScheduledDB");
        IOHelper.mkdirs(directory);
        IOHelper.deleteChildren(directory);
        store.setDirectory(directory);
        final int NUMBER = 1000;
        store.start();
        List<ByteSequence> list = new ArrayList<ByteSequence>();
        for (int i = 0; i < NUMBER; i++) {
            ByteSequence buff = new ByteSequence(new String("testjob" + i).getBytes());
            list.add(buff);
        }

        JobScheduler js = store.getJobScheduler("test");
        js.startDispatching();
        int count = 0;
        long startTime = 10 * 60 * 1000;
        long period = startTime;
        for (ByteSequence job : list) {
            js.schedule("id:" + (count++), job, "", startTime, period, -1);
        }

        List<Job> test = js.getAllJobs();
        LOG.debug("Found {} jobs in the store before restart", test.size());
        assertEquals(list.size(), test.size());
        store.stop();

        store.start();
        js = store.getJobScheduler("test");
        test = js.getAllJobs();
        LOG.debug("Found {} jobs in the store after restart", test.size());
        assertEquals(list.size(), test.size());

        for (int i = 0; i < list.size(); i++) {
            String orig = new String(list.get(i).getData());
            String payload = new String(test.get(i).getPayload());
            assertEquals(orig, payload);
        }
    }

};