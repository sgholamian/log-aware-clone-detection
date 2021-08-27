//,temp,BoxFoldersManagerIT.java,224,237,temp,BoxFoldersManagerIT.java,110,133
//,3
public class xxx {
    @Test
    public void testCopyFolder() throws Exception {
        com.box.sdk.BoxFolder result = null;
        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.folderId", testFolder.getID());
            // parameter type is String
            headers.put("CamelBox.destinationFolderId", CAMEL_TEST_DESTINATION_FOLDER_ID);
            // parameter type is String
            headers.put("CamelBox.newName", CAMEL_TEST_COPY_FOLDER);
            result = requestBodyAndHeaders("direct://COPYFOLDER", null, headers);
            assertNotNull(result, "copyFolder result");
            assertEquals(CAMEL_TEST_COPY_FOLDER, result.getInfo().getName(), "copyFolder folder name");
            LOG.debug("copyFolder: " + result);
        } finally {
            if (result != null) {
                try {
                    result.delete(true);
                } catch (Throwable t) {
                }
            }
        }
    }

};