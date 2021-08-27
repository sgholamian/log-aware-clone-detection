//,temp,JaxbDataFormatMultipleNamespacesTest.java,44,77,temp,JaxbMarshalNamespacePrefixMapperTest.java,51,77
//,3
public class xxx {
    @Test
    public void testNamespacePrefix() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);

        Order order = new Order();
        order.setId("1");
        Address address = new Address();
        address.setStreet("Main Street");
        address.setStreetNumber("3a");
        address.setZip("65843");
        address.setCity("Sulzbach");
        order.setAddress(address);

        template.sendBody("direct:start", order);

        assertMockEndpointsSatisfied();

        String xml = mock.getExchanges().get(0).getIn().getBody(String.class);
        LOG.info(xml);

        assertTrue(xml.contains("xmlns:a=\"http://www.camel.apache.org/jaxb/example/address/1\""));
        assertTrue(xml.contains("xmlns:o=\"http://www.camel.apache.org/jaxb/example/order/1\""));
        assertTrue(xml.contains("<o:id>1</o:id>"));
        assertTrue(xml.contains("<a:street>Main Street</a:street>"));
        assertTrue(xml.contains("</o:order>"));
    }

};