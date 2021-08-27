//,temp,CalendarSettingsIT.java,40,47,temp,DriveRevisionsIT.java,41,51
//,3
public class xxx {
    @Test
    public void testList() throws Exception {
        File testFile = uploadTestFile();
        String fileId = testFile.getId();

        // using String message body for single parameter "fileId"
        final com.google.api.services.drive.model.RevisionList result = requestBody("direct://LIST", fileId);

        assertNotNull(result, "list result");
        LOG.debug("list: " + result);
    }

};