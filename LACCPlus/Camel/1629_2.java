//,temp,ManagedBrowsableEndpointAsXmlTest.java,244,271,temp,ManagedBrowsableEndpointAsXmlTest.java,126,157
//,3
public class xxx {
    @Test
    public void testBrowseableEndpointAsXml() throws Exception {
        getMockEndpoint("mock:result").expectedMessageCount(2);

        template.sendBodyAndHeader("direct:start", "Hello World", "foo", 123);
        template.sendBodyAndHeader("direct:start", "Bye World", "foo", 456);

        assertMockEndpointsSatisfied();

        List<Exchange> exchanges = getMockEndpoint("mock:result").getReceivedExchanges();

        MBeanServer mbeanServer = getMBeanServer();

        ObjectName name = getCamelObjectName(TYPE_ENDPOINT, "mock://result");

        String out = (String) mbeanServer.invoke(name, "browseMessageAsXml", new Object[] { 0, false },
                new String[] { "java.lang.Integer", "java.lang.Boolean" });
        assertNotNull(out);
        log.info(out);

        assertEquals("<message exchangeId=\"" + exchanges.get(0).getExchangeId()
                     + "\">\n  <headers>\n    <header key=\"foo\" type=\"java.lang.Integer\">123</header>\n  </headers>\n</message>",
                out);

        out = (String) mbeanServer.invoke(name, "browseMessageAsXml", new Object[] { 1, false },
                new String[] { "java.lang.Integer", "java.lang.Boolean" });
        assertNotNull(out);
        log.info(out);
        assertEquals("<message exchangeId=\"" + exchanges.get(1).getExchangeId()
                     + "\">\n  <headers>\n    <header key=\"foo\" type=\"java.lang.Integer\">456</header>\n  </headers>\n</message>",
                out);
    }

};