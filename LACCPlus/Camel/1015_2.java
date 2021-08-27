//,temp,BoxTasksManagerIT.java,197,212,temp,BoxSearchManagerIT.java,52,65
//,3
public class xxx {
    @Test
    public void testSearchFolder() {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", "0");
        // parameter type is String
        headers.put("CamelBox.query", "*");

        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBodyAndHeaders("direct://SEARCHFOLDER", null, headers);

        assertNotNull(result, "searchFolder result");
        LOG.debug("searchFolder: " + result);
    }

};