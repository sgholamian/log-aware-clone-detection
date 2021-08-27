//,temp,BoxTasksManagerIT.java,197,212,temp,BoxSearchManagerIT.java,52,65
//,3
public class xxx {
    @Disabled // No way to change BoxTask.Info parameters
    @Test
    public void testUpdateTaskInfo() throws Exception {
        BoxTask.Info info = testTask.getInfo();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.taskId", testTask.getID());
        // parameter type is com.box.sdk.BoxTask.Info
        headers.put("CamelBox.info", info);

        final com.box.sdk.BoxTask result = requestBodyAndHeaders("direct://UPDATETASKINFO", null, headers);

        assertNotNull(result, "updateTaskInfo result");
        LOG.debug("updateTaskInfo: " + result);
    }

};