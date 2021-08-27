//,temp,FhirOperationIT.java,142,162,temp,BoxFilesManagerIT.java,96,114
//,3
public class xxx {
    @Test
    public void testCreateFileMetadata() throws Exception {
        Metadata metadata = new Metadata();
        metadata.add("/foo", "bar");

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is com.box.sdk.Metadata
        headers.put("CamelBox.metadata", metadata);
        // parameter type is String
        headers.put("CamelBox.typeName", null);

        final com.box.sdk.Metadata result = requestBodyAndHeaders("direct://CREATEFILEMETADATA", null, headers);

        assertNotNull(result, "createFileMetadata result");
        assertEquals("bar", result.get("/foo"), "createFileMetadata result");
        LOG.debug("createFileMetadata: " + result);
    }

};