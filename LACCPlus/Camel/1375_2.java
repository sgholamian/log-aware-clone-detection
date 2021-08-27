//,temp,BoxCommentsManagerIT.java,76,93,temp,BoxCollaborationsManagerIT.java,143,161
//,3
public class xxx {
    @Test
    public void testUpdateCollaborationInfo() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.collaborationId", testCollaboration.getID());
        // parameter type is com.box.sdk.BoxCollaboration.Info

        BoxCollaboration.Info info = testCollaboration.getInfo();
        info.setRole(BoxCollaboration.Role.PREVIEWER);
        headers.put("CamelBox.info", info);

        final com.box.sdk.BoxCollaboration result = requestBodyAndHeaders("direct://UPDATECOLLABORATIONINFO", null,
                headers);

        assertNotNull(result, "updateCollaborationInfo result");
        assertNotNull(result.getInfo(), "updateCollaborationInfo info");
        assertEquals(BoxCollaboration.Role.PREVIEWER, result.getInfo().getRole(), "updateCollaborationInfo info");
        LOG.debug("updateCollaborationInfo: " + result);
    }

};