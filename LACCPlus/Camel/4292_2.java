//,temp,ManagedBrowsableEndpointAsXmlTest.java,187,213,temp,ManagedBrowsableEndpointAsXmlTest.java,159,185
//,3
public class xxx {
    @Test
    public void testBrowseableEndpointAsXmlAllIncludeBody() throws Exception {
        getMockEndpoint("mock:result").expectedMessageCount(2);

        template.sendBody("direct:start", "Hello World");
        template.sendBodyAndHeader("direct:start", "Bye World", "foo", 456);

        assertMockEndpointsSatisfied();

        List<Exchange> exchanges = getMockEndpoint("mock:result").getReceivedExchanges();

        MBeanServer mbeanServer = getMBeanServer();

        ObjectName name = getCamelObjectName(TYPE_ENDPOINT, "mock://result");

        String out = (String) mbeanServer.invoke(name, "browseAllMessagesAsXml", new Object[] { true },
                new String[] { "java.lang.Boolean" });
        assertNotNull(out);
        log.info(out);

        assertEquals("<messages>\n<message exchangeId=\"" + exchanges.get(0).getExchangeId()
                     + "\">\n  <body type=\"java.lang.String\">Hello World</body>\n</message>\n"
                     + "<message exchangeId=\"" + exchanges.get(1).getExchangeId()
                     + "\">\n  <headers>\n    <header key=\"foo\" type=\"java.lang.Integer\">456</header>\n  </headers>\n"
                     + "  <body type=\"java.lang.String\">Bye World</body>\n</message>\n</messages>",
                out);
    }

};