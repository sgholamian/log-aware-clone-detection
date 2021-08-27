//,temp,AccountIT.java,45,52,temp,BoxTasksManagerIT.java,134,142
//,3
public class xxx {
    @Test
    public void testGetFileTasks() throws Exception {
        // using String message body for single parameter "fileId"
        @SuppressWarnings("rawtypes")
        final java.util.List result = requestBody("direct://GETFILETASKS", testFile.getID());

        assertNotNull(result, "getFileTasks result");
        LOG.debug("getFileTasks: " + result);
    }

};