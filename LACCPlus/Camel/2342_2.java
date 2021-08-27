//,temp,ManagedFromRestPlaceholderTest.java,45,82,temp,ManagedFromRestGetTest.java,44,84
//,3
public class xxx {
    @Test
    public void testFromRestModel() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        ObjectName on = getContextObjectName();

        String xml = (String) mbeanServer.invoke(on, "dumpRestsAsXml", null, null);
        assertNotNull(xml);
        log.info(xml);

        assertTrue(xml.contains("<rests"));
        assertTrue(xml.contains("<rest path=\"/say/hello\">"));
        assertTrue(xml.contains("<rest path=\"/say/bye\">"));
        assertTrue(xml.contains("</rest>"));
        assertTrue(xml.contains("<get"));
        assertTrue(xml.contains("application/json"));
        assertTrue(xml.contains("<post"));
        assertTrue(xml.contains("application/json"));
        assertTrue(xml.contains("</rests>"));

        assertTrue(xml.contains("<param collectionFormat=\"multi\" dataType=\"string\" defaultValue=\"b\" "
                                + "description=\"header param description2\" name=\"header_letter\" required=\"false\" type=\"query\">"));
        assertTrue(xml.contains("<param dataType=\"integer\" defaultValue=\"1\" "
                                + "description=\"header param description1\" name=\"header_count\" required=\"true\" type=\"header\">"));
        assertTrue(xml.contains("<value>1</value>"));
        assertTrue(xml.contains("<value>a</value>"));

        assertTrue(xml.contains("<responseMessage code=\"300\" message=\"test msg\" responseModel=\"java.lang.Integer\"/>"));

        String xml2 = (String) mbeanServer.invoke(on, "dumpRoutesAsXml", null, null);
        log.info(xml2);
        // and we should have rest in the routes that indicate its from a rest dsl
        assertTrue(xml2.contains("rest=\"true\""));

        assertTrue(xml2.matches("[\\S\\s]* <to id=\"to[0-9]+\" uri=\"direct:hello\"/>[\\S\\s]*"));
        assertTrue(xml2.matches("[\\S\\s]*<to id=\"to[0-9]+\" uri=\"direct:bye\"/>[\\S\\s]*"));
        assertTrue(xml2.matches("[\\S\\s]*<to id=\"to[0-9]+\" uri=\"mock:update\"/>[\\S\\s]*"));

        // there should be 3 + 2 routes
        assertEquals(3 + 2, context.getRouteDefinitions().size());
    }

};