//,temp,CalendarSettingsIT.java,40,47,temp,DriveRevisionsIT.java,41,51
//,3
public class xxx {
    @Test
    public void testGet() throws Exception {
        // using String message body for single parameter "setting"
        final com.google.api.services.calendar.model.Setting result = requestBody("direct://GET", "timezone");

        assertNotNull(result, "get result");
        LOG.debug("get: " + result);
    }

};