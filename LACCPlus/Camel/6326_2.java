//,temp,BoxCommentsManagerIT.java,59,74,temp,BoxFilesManagerIT.java,389,409
//,3
public class xxx {
    @Test
    public void testUpdateFileMetadata() throws Exception {
        Metadata metadata = new Metadata();
        metadata = testFile.createMetadata(metadata);

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is com.box.sdk.Metadata
        headers.put("CamelBox.metadata", metadata);

        //metada has to contain some value, otherwise response result will be error code 400
        metadata.add("/foo", "bar");

        final com.box.sdk.Metadata result = requestBodyAndHeaders("direct://UPDATEFILEMETADATA", null, headers);

        assertNotNull(result, "updateFileMetadata result");
        assertNotNull(result.get("/foo"), "updateFileMetadata property foo");
        assertEquals("bar", metadata.get("/foo"));
        LOG.debug("updateFileMetadata: " + result);
    }

};