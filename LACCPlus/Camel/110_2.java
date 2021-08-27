//,temp,CalendarColorsIT.java,40,46,temp,DriveChangesIT.java,60,66
//,2
public class xxx {
    @Test
    public void testList() throws Exception {
        final com.google.api.services.drive.model.ChangeList result = requestBody("direct://LIST", null);

        assertNotNull(result, "list result");
        LOG.debug("list: " + result);
    }

};