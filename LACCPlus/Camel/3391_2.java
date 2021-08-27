//,temp,DrivePermissionsIT.java,40,50,temp,CalendarSettingsIT.java,49,55
//,3
public class xxx {
    @Test
    public void testList() throws Exception {
        final com.google.api.services.calendar.model.Settings result = requestBody("direct://LIST", null);

        assertNotNull(result, "list result");
        LOG.debug("list: " + result);
    }

};