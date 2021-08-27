//,temp,BoxUsersManagerIT.java,221,227,temp,BoxFoldersManagerIT.java,154,162
//,3
public class xxx {
    @Test
    public void testGetFolder() throws Exception {
        // using String[] message body for single parameter "path"
        final com.box.sdk.BoxFolder result = requestBody("direct://GETFOLDER", new String[] { CAMEL_TEST_FOLDER });

        assertNotNull(result, "getFolder result");
        assertEquals(testFolder.getID(), result.getID(), "getFolder folder id");
        LOG.debug("getFolder: " + result);
    }

};