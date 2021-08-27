//,temp,JobSchedulerJmxManagementTests.java,96,110,temp,JobSchedulerJmxManagementTests.java,66,81
//,3
public class xxx {
    @Test
    public void testRemvoeJob() throws Exception {
        JobSchedulerViewMBean view = getJobSchedulerMBean();
        assertNotNull(view);
        assertTrue(view.getAllJobs().isEmpty());
        scheduleMessage(60000, 0, 0);
        assertFalse(view.getAllJobs().isEmpty());
        TabularData jobs = view.getAllJobs();
        assertEquals(1, jobs.size());
        for (Object key : jobs.keySet()) {
            String jobId = ((List<?>)key).get(0).toString();
            LOG.info("Attempting to remove Job: {}", jobId);
            view.removeJob(jobId);
        }
        assertTrue(view.getAllJobs().isEmpty());
    }

};