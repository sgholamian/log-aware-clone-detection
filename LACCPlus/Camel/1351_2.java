//,temp,BoxFilesManagerIT.java,305,328,temp,BoxFilesManagerIT.java,248,262
//,3
public class xxx {
    @Test
    public void testGetFileMetadata() throws Exception {
        testFile.createMetadata(new Metadata());

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is String
        headers.put("CamelBox.typeName", null);

        final com.box.sdk.Metadata result = requestBodyAndHeaders("direct://GETFILEMETADATA", null, headers);

        assertNotNull(result, "getFileMetadata result");
        LOG.debug("getFileMetadata: " + result);
    }

};