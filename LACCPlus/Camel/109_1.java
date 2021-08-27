//,temp,BoxGroupsManagerIT.java,129,136,temp,BoxTasksManagerIT.java,188,195
//,2
public class xxx {
    @Test
    public void testGetGroupInfo() throws Exception {
        // using String message body for single parameter "groupId"
        final com.box.sdk.BoxGroup.Info result = requestBody("direct://GETGROUPINFO", testGroup.getID());

        assertNotNull(result, "getGroupInfo result");
        LOG.debug("getGroupInfo: " + result);
    }

};