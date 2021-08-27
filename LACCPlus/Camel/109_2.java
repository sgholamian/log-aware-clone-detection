//,temp,BoxGroupsManagerIT.java,129,136,temp,BoxTasksManagerIT.java,188,195
//,2
public class xxx {
    @Test
    public void testGetTaskInfo() throws Exception {
        // using String message body for single parameter "taskId"
        final com.box.sdk.BoxTask.Info result = requestBody("direct://GETTASKINFO", testTask.getID());

        assertNotNull(result, "getTaskInfo result");
        LOG.debug("getTaskInfo: " + result);
    }

};