//,temp,BoxGroupsManagerIT.java,170,178,temp,BoxCollaborationsManagerIT.java,124,132
//,2
public class xxx {
    @Test
    public void testGetGroupMemberships() throws Exception {
        // using String message body for single parameter "groupId"
        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBody("direct://GETGROUPMEMBERSHIPS", testGroup.getID());

        assertNotNull(result, "getGroupMemberships result");
        LOG.debug("getGroupMemberships: " + result);
    }

};