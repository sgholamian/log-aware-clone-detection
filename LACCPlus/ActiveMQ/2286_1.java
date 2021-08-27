//,temp,JobSchedulerTest.java,180,209,temp,InMemoryJobSchedulerTest.java,184,213
//,3
public class xxx {
    @Test
    public void testGetExecutionCount() throws Exception {
        final String jobId = "Job-1";
        long time = 10000;
        final CountDownLatch done = new CountDownLatch(10);

        String str = new String("test");
        scheduler.schedule(jobId, new ByteSequence(str.getBytes()), "", time, 1000, 10);

        int size = scheduler.getAllJobs().size();
        assertEquals(size, 1);

        scheduler.addListener(new JobListener() {
            @Override
            public void scheduledJob(String id, ByteSequence job) {
                LOG.info("Job exectued: {}", 11 - done.getCount());
                done.countDown();
            }
        });

        List<Job> jobs = scheduler.getNextScheduleJobs();
        assertEquals(1, jobs.size());
        Job job = jobs.get(0);
        assertEquals(jobId, job.getJobId());
        assertEquals(0, job.getExecutionCount());
        assertTrue("Should have fired ten times.", done.await(60, TimeUnit.SECONDS));
        // The job is not updated on the last firing as it is removed from the store following
        // it's last execution so the count will always be one less than the max firings.
        assertTrue(job.getExecutionCount() >= 9);
    }

};