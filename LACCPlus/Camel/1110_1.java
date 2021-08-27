//,temp,BoxFoldersManagerIT.java,180,197,temp,SubscriptionGatewayIT.java,101,114
//,3
public class xxx {
    @Test
    public void testGetFolderItems() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", CAMEL_TEST_ROOT_FOLDER_ID);
        // parameter type is Long
        headers.put("CamelBox.offset", null);
        // parameter type is Long
        headers.put("CamelBox.limit", null);
        // parameter type is String[]
        headers.put("CamelBox.fields", null);

        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBodyAndHeaders("direct://GETFOLDERITEMS", null, headers);

        assertNotNull(result, "getFolderItems result");
        LOG.debug("getFolderItems: " + result);
    }

};