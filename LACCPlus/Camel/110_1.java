//,temp,CalendarColorsIT.java,40,46,temp,DriveChangesIT.java,60,66
//,2
public class xxx {
    @Test
    public void testGet() throws Exception {
        com.google.api.services.calendar.model.Colors result = requestBody("direct://GET", null);

        assertNotNull(result, "get result");
        LOG.debug("get: " + result);
    }

};