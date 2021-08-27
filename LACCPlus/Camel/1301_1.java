//,temp,BoxGroupsManagerIT.java,138,157,temp,BoxGroupsManagerIT.java,59,74
//,3
public class xxx {
    @Test
    public void testUpdateGroupInfo() throws Exception {
        BoxGroup.Info info = testGroup.getInfo();
        info.setDescription(CAMEL_TEST_GROUP_DESCRIPTION);

        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.groupId", testGroup.getID());
            // parameter type is com.box.sdk.BoxGroup.Info
            headers.put("CamelBox.groupInfo", info);
            final com.box.sdk.BoxGroup result = requestBodyAndHeaders("direct://UPDATEGROUPINFO", null, headers);
            assertNotNull(result, "updateGroupInfo result");
            LOG.debug("updateGroupInfo: " + result);
        } finally {
            info = testGroup.getInfo();
            info.setDescription("");
            testGroup.updateInfo(info);
        }
    }

};