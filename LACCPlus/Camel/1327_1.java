//,temp,BoxFilesManagerIT.java,234,246,temp,BoxUsersManagerIT.java,206,219
//,3
public class xxx {
    @Test
    public void testGetFileInfo() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is String[]
        headers.put("CamelBox.fields", null);

        final com.box.sdk.BoxFile.Info result = requestBodyAndHeaders("direct://GETFILEINFO", null, headers);

        assertNotNull(result, "getFileInfo result");
        LOG.debug("getFileInfo: " + result);
    }

};