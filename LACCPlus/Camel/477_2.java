//,temp,SubscriptionGatewayIT.java,41,49,temp,DriveFilesIT.java,105,113
//,3
public class xxx {
    @Test
    public void testInsert() throws Exception {
        File file = new File();
        file.setTitle(UPLOAD_FILE.getName());
        // using com.google.api.services.drive.model.File message body for single parameter "content"
        File result = requestBody("direct://INSERT", file);
        assertNotNull(result, "insert result");
        LOG.debug("insert: " + result);
    }

};