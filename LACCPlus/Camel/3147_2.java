//,temp,CreateRouteWithNonExistingEndpointTest.java,34,45,temp,SendToNonExistingEndpointTest.java,28,37
//,3
public class xxx {
    @Test
    public void testSendToNonExistingEndpoint() throws Exception {
        try {
            template.sendBody("thisUriDoesNotExist", "<hello>world!</hello>");
            fail("Should have failed to send this message!");
        } catch (NoSuchEndpointException e) {
            log.debug("Caught expected exception: " + e, e);
            assertEquals("thisUriDoesNotExist", e.getUri(), "uri");
        }
    }

};