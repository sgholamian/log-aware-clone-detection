//,temp,BoxFilesManagerIT.java,183,202,temp,BoxTasksManagerIT.java,166,186
//,3
public class xxx {
    @Disabled
    //needs https://community.box.com/t5/custom/page/page-id/BoxViewTicketDetail?ticket_id=1895413 to be solved
    @Test
    public void testGetTaskAssignments() throws Exception {
        // using String message body for single parameter "taskId"

        //add assignment to task -> to be able to search for assignments
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.taskId", testTask.getID());
        // parameter type is com.box.sdk.BoxUser
        headers.put("CamelBox.assignTo", getCurrentUser());

        requestBodyAndHeaders("direct://ADDASSIGNMENTTOTASK", null, headers);

        @SuppressWarnings("rawtypes")
        final java.util.List result = requestBody("direct://GETTASKASSIGNMENTS", testTask.getID());

        assertNotNull(result, "getTaskAssignments result");
        LOG.debug("getTaskAssignments: " + result);
    }

};