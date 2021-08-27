//,temp,CxfProducerTest.java,151,167,temp,CxfProducerTest.java,112,132
//,3
public class xxx {
    @Test
    public void testInvokingJaxWsServerWithParams() throws Exception {
        Exchange exchange = sendJaxWsMessage();

        org.apache.camel.Message out = exchange.getMessage();
        String result = out.getBody(String.class);
        LOG.info("Received output text: " + result);
        Map<String, Object> responseContext = CastUtils.cast((Map<?, ?>) out.getHeader(Client.RESPONSE_CONTEXT));
        assertNotNull(responseContext);
        assertEquals("{http://apache.org/hello_world_soap_http}greetMe",
                responseContext.get("javax.xml.ws.wsdl.operation").toString(), "Get the wrong wsdl operation name");
        assertEquals("Hello " + TEST_MESSAGE, result, "reply body on Camel");

        // check the other camel header copying
        String fileName = out.getHeader(Exchange.FILE_NAME, String.class);
        assertEquals("testFile", fileName, "Should get the file name from out message header");
    }

};