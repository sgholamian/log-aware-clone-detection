//,temp,BoxFilesManagerIT.java,347,369,temp,BoxFilesManagerIT.java,330,345
//,3
public class xxx {
    @Disabled // Requires premium user account to test
    @Test
    public void testPromoteFileVersion() throws Exception {
        testFile.uploadVersion(getClass().getResourceAsStream(CAMEL_TEST_FILE));

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is Integer
        headers.put("CamelBox.version", 1);

        final com.box.sdk.BoxFileVersion result = requestBodyAndHeaders("direct://PROMOTEFILEVERSION", null, headers);

        assertNotNull(result, "promoteFileVersion result");
        LOG.debug("promoteFileVersion: " + result);
    }

};