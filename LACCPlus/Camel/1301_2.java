//,temp,BoxGroupsManagerIT.java,138,157,temp,BoxGroupsManagerIT.java,59,74
//,3
public class xxx {
    @Test
    public void testAddGroupMembership() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.groupId", testGroup.getID());
        // parameter type is String
        headers.put("CamelBox.userId", testUser.getID());
        // parameter type is com.box.sdk.BoxGroupMembership.Role
        headers.put("CamelBox.role", null);

        final com.box.sdk.BoxGroupMembership result = requestBodyAndHeaders("direct://ADDGROUPMEMBERSHIP", null,
                headers);

        assertNotNull(result, "addGroupMembership result");
        LOG.debug("addGroupMembership: " + result);
    }

};