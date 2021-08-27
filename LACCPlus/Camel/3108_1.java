//,temp,ProcessorAsEndpointTest.java,46,55,temp,LogRouteTest.java,44,52
//,3
public class xxx {
    @Test
    public void testSendingToNonExistentEndpoint() throws Exception {
        String uri = "unknownEndpoint";
        try {
            template.sendBody(uri, body);
            fail("We should have failed as this is a bad endpoint URI");
        } catch (NoSuchEndpointException e) {
            log.debug("Caught expected exception: " + e, e);
        }
    }

};