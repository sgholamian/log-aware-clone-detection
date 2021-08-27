//,temp,KahaDBSchedulerMissingJournalLogsTest.java,128,159,temp,KahaDBSchedulerMissingJournalLogsTest.java,99,126
//,3
public class xxx {
    @Test(timeout=120 * 1000)
    public void testMissingLogsCausesBrokerToFail() throws Exception {
        fillUpSomeLogFiles();

        int jobCount = schedulerStore.getJobScheduler("JMS").getAllJobs().size();
        LOG.info("There are {} jobs in the store.", jobCount);

        List<File> toDelete = new ArrayList<File>();
        Map<Integer, DataFile> files = schedulerStore.getJournal().getFileMap();
        for (int i = files.size(); i > files.size() / 2; i--) {
            toDelete.add(files.get(i).getFile());
        }

        broker.stop();
        broker.waitUntilStopped();

        for (File file : toDelete) {
            LOG.info("File to delete: {}", file);
            IOHelper.delete(file);
        }

        try {
            createBroker();
            broker.start();
            fail("Should not start when logs are missing.");
        } catch (Exception e) {
        }
    }

};