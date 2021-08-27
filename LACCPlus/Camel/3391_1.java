//,temp,DrivePermissionsIT.java,40,50,temp,CalendarSettingsIT.java,49,55
//,3
public class xxx {
    @Test
    public void testList() throws Exception {
        File testFile = uploadTestFile();
        String fileId = testFile.getId();

        // using String message body for single parameter "fileId"
        final com.google.api.services.drive.model.PermissionList result = requestBody("direct://LIST", fileId);

        assertNotNull(result, "list result");
        LOG.debug("list: " + result);
    }

};