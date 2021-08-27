//,temp,BoxFoldersManagerIT.java,80,97,temp,BoxFoldersManagerIT.java,61,78
//,3
public class xxx {
    @Test
    public void testCreateFolderByPath() throws Exception {

        // delete folder created in test setup.
        deleteTestFolder();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.parentFolderId", "0");
        // parameter type is String[]
        headers.put("CamelBox.path", new String[] { CAMEL_TEST_FOLDER });

        testFolder = requestBodyAndHeaders("direct://CREATEFOLDER", null, headers);

        assertNotNull(testFolder, "createFolder result");
        assertEquals(CAMEL_TEST_FOLDER, testFolder.getInfo().getName(), "createFolder folder name");
        LOG.debug("createFolder: " + testFolder);
    }

};