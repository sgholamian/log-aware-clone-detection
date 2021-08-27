//,temp,BoxFilesManagerIT.java,204,223,temp,BoxFilesManagerIT.java,71,94
//,3
public class xxx {
    @Test
    public void testCopyFile() throws Exception {
        com.box.sdk.BoxFile result = null;

        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.fileId", testFile.getID());
            // parameter type is String
            headers.put("CamelBox.destinationFolderId", "0");
            // parameter type is String
            headers.put("CamelBox.newName", CAMEL_TEST_COPY_FILE_NAME);

            result = requestBodyAndHeaders("direct://COPYFILE", null, headers);

            assertNotNull(result, "copyFile result");
            assertEquals(CAMEL_TEST_COPY_FILE_NAME, result.getInfo().getName(), "copyFile name");
            LOG.debug("copyFile: " + result);
        } finally {
            if (result != null) {
                result.delete();
            }
        }
    }

};