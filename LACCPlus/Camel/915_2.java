//,temp,DumpModelAsXmlChoiceFilterRouteTest.java,60,69,temp,RestApiMatchUriNettyTest.java,33,42
//,3
public class xxx {
    @Test
    public void testApi() throws Exception {
        String out = template.requestBody("netty-http:http://localhost:{{port}}/api-doc", null, String.class);
        assertNotNull(out);
        log.info(out);

        assertTrue(out.contains("\"version\" : \"1.2.3\""));
        assertTrue(out.contains("\"title\" : \"The hello rest thing\""));
        assertTrue(out.contains("\"/hello/hi/{name}\""));
    }

};