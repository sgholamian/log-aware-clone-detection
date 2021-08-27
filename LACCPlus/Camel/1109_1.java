//,temp,BoxFoldersManagerIT.java,207,222,temp,DriveFilesIT.java,54,73
//,3
public class xxx {
    @Test
    public void testMoveFolder() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", testFolder.getID());
        // parameter type is String
        headers.put("CamelBox.destinationFolderId", CAMEL_TEST_DESTINATION_FOLDER_ID);
        // parameter type is String
        headers.put("CamelBox.newName", CAMEL_TEST_MOVE_FOLDER);

        final com.box.sdk.BoxFolder result = requestBodyAndHeaders("direct://MOVEFOLDER", null, headers);

        assertNotNull(result, "moveFolder result");
        assertEquals(CAMEL_TEST_MOVE_FOLDER, result.getInfo().getName(), "moveFolder folder name");
        LOG.debug("moveFolder: " + result);
    }

};