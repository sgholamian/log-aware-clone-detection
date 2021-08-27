//,temp,ManagedBrowsableEndpointAsXmlTest.java,187,213,temp,ManagedBrowsableEndpointAsXmlTest.java,159,185
//,3
public class xxx {
    @Test
    public void testBrowseableEndpointAsXmlAll() throws Exception {
        getMockEndpoint("mock:result").expectedMessageCount(2);

        template.sendBodyAndHeader("direct:start", "Hello World", "foo", 123);
        template.sendBodyAndHeader("direct:start", "Bye World", "foo", 456);

        assertMockEndpointsSatisfied();

        MBeanServer mbeanServer = getMBeanServer();

        List<Exchange> exchanges = getMockEndpoint("mock:result").getReceivedExchanges();

        ObjectName name = getCamelObjectName(TYPE_ENDPOINT, "mock://result");

        String out = (String) mbeanServer.invoke(name, "browseAllMessagesAsXml", new Object[] { false },
                new String[] { "java.lang.Boolean" });
        assertNotNull(out);
        log.info(out);

        assertEquals("<messages>\n<message exchangeId=\"" + exchanges.get(0).getExchangeId() + "\">\n  <headers>\n"
                     + "    <header key=\"foo\" type=\"java.lang.Integer\">123</header>\n  </headers>\n</message>\n"
                     + "<message exchangeId=\"" + exchanges.get(1).getExchangeId()
                     + "\">\n  <headers>\n    <header key=\"foo\" type=\"java.lang.Integer\">456</header>\n  </headers>\n"
                     + "</message>\n</messages>",
                out);
    }

};