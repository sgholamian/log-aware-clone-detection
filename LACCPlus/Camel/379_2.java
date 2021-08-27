//,temp,DrivePropertiesIT.java,41,51,temp,DriveAboutIT.java,40,46
//,3
public class xxx {
    @Test
    public void testGet() throws Exception {
        final com.google.api.services.drive.model.About result = requestBody("direct://GET", null);

        assertNotNull(result, "get result");
        LOG.debug("get: " + result);
    }

};