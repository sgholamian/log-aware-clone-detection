//,temp,BoxFoldersManagerIT.java,199,205,temp,SubscriptionGatewayIT.java,79,87
//,3
public class xxx {
    @Test
    public void testGetRootFolder() throws Exception {
        final com.box.sdk.BoxFolder result = requestBody("direct://GETROOTFOLDER", null);

        assertNotNull(result, "getRootFolder result");
        LOG.debug("getRootFolder: " + result);
    }

};