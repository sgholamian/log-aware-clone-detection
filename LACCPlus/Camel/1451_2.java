//,temp,CxfProducerTest.java,151,167,temp,CxfProducerTest.java,112,132
//,3
public class xxx {
    @Test
    public void testInvokingSimpleServerWithParams() throws Exception {
        Exchange exchange = sendSimpleMessage();

        org.apache.camel.Message out = exchange.getMessage();
        String result = out.getBody(String.class);
        LOG.info("Received output text: " + result);
        Map<String, Object> responseContext = CastUtils.cast((Map<?, ?>) out.getHeader(Client.RESPONSE_CONTEXT));
        assertNotNull(responseContext);
        assertEquals("UTF-8", responseContext.get(org.apache.cxf.message.Message.ENCODING),
                "We should get the response context here");
        assertEquals("echo " + TEST_MESSAGE, result, "reply body on Camel");

        // check the other camel header copying
        String fileName = out.getHeader(Exchange.FILE_NAME, String.class);
        assertEquals("testFile", fileName, "Should get the file name from out message header");

        // check if the header object is turned into String
        Object requestObject = out.getHeader("requestObject");
        assertTrue(requestObject instanceof DefaultCxfBinding, "We should get the right requestObject.");
    }

};