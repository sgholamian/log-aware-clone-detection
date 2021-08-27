//,temp,BoxFilesManagerIT.java,347,369,temp,BoxFilesManagerIT.java,330,345
//,3
public class xxx {
    @Test
    public void testRenameFile() throws Exception {

        com.box.sdk.BoxFile result = null;

        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.fileId", testFile.getID());
            // parameter type is String
            headers.put("CamelBox.newFileName", CAMEL_TEST_RENAME_FILE_NAME);

            result = requestBodyAndHeaders("direct://RENAMEFILE", null, headers);

            assertNotNull(result, "renameFile result");
            assertEquals(CAMEL_TEST_RENAME_FILE_NAME, result.getInfo().getName(), "renameFile name");
            LOG.debug("renameFile: " + result);
        } finally {
            if (result != null) {
                result.delete();
            }
        }
    }

};