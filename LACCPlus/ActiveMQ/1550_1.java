//,temp,JobSchedulerJmxManagementTests.java,96,110,temp,JobSchedulerJmxManagementTests.java,66,81
//,3
public class xxx {
    @Test
    public void testGetNextScheduledJob() throws Exception {
        JobSchedulerViewMBean view = getJobSchedulerMBean();
        assertNotNull(view);
        assertTrue(view.getAllJobs().isEmpty());
        scheduleMessage(60000, 0, 0);
        assertFalse(view.getAllJobs().isEmpty());
        long before = System.currentTimeMillis() + 57 * 1000;
        long toLate = System.currentTimeMillis() + 63 * 1000;
        String next = view.getNextScheduleTime();
        long nextTime = JobSupport.getDataTime(next);
        LOG.info("Next Scheduled Time: {} should be after: {}", next, JobSupport.getDateTime(before));
        assertTrue(nextTime > before);
        assertTrue(nextTime < toLate);
    }

};