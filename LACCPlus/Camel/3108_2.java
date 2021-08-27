//,temp,ProcessorAsEndpointTest.java,46,55,temp,LogRouteTest.java,44,52
//,3
public class xxx {
    @Test
    public void testSendMessageToBadLevel() throws Exception {
        try {
            template.sendBody("log:org.apache.camel.TEST?level=noSuchLevel", "<level>noSuchLevel</level>");
            fail("Should have failed!");
        } catch (Exception e) {
            LOG.debug("Caught expected exception: " + e, e);
        }
    }

};