//,temp,BoxFilesManagerIT.java,295,303,temp,BoxGroupsManagerIT.java,120,127
//,3
public class xxx {
    @Test
    public void testGetAllGroups() throws Exception {
        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBody("direct://GETALLGROUPS", null);

        assertNotNull(result, "getAllGroups result");
        LOG.debug("getAllGroups: " + result);
    }

};