//,temp,BoxTasksManagerIT.java,64,80,temp,DriveFilesIT.java,254,267
//,3
public class xxx {
    @Disabled
    //needs https://community.box.com/t5/custom/page/page-id/BoxViewTicketDetail?ticket_id=1895413 to be solved
    @Test
    public void testAddAssignmentToTask() throws Exception {
        com.box.sdk.BoxTask result = null;

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.taskId", testTask.getID());
        // parameter type is com.box.sdk.BoxUser
        headers.put("CamelBox.assignTo", getCurrentUser());

        result = requestBodyAndHeaders("direct://ADDASSIGNMENTTOTASK", null, headers);

        assertNotNull(result, "addAssignmentToTask result");
        LOG.debug("addAssignmentToTask: " + result);
    }

};