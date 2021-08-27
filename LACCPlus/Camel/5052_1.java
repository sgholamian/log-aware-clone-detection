//,temp,JaxbDataFormatMultipleNamespacesTest.java,44,77,temp,JaxbMarshalNamespacePrefixMapperTest.java,51,77
//,3
public class xxx {
    @Test
    public void testMarshallMultipleNamespaces() throws Exception {
        mockMarshall.expectedMessageCount(1);

        Order order = new Order();
        order.setId("1");
        Address address = new Address();
        address.setStreet("Main Street");
        address.setStreetNumber("3a");
        address.setZip("65843");
        address.setCity("Sulzbach");
        order.setAddress(address);
        template.sendBody("direct:marshall", order);

        assertMockEndpointsSatisfied();

        String payload = mockMarshall.getExchanges().get(0).getIn().getBody(String.class);
        LOG.info(payload);

        assertTrue(payload.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
        assertTrue(payload.contains("<order:order"));
        assertTrue(payload.contains("<order:id>1</order:id>"));
        assertTrue(payload.contains("<address:address>"));
        assertTrue(payload.contains("<address:street>Main Street</address:street>"));
        assertTrue(payload.contains("<address:streetNumber>3a</address:streetNumber>"));
        assertTrue(payload.contains("<address:zip>65843</address:zip>"));
        assertTrue(payload.contains("<address:city>Sulzbach</address:city>"));
        assertTrue(payload.contains("</address:address>"));
        assertTrue(payload.contains("</order:order>"));

        // the namespaces
        assertTrue(payload.contains("xmlns:address=\"http://www.camel.apache.org/jaxb/example/address/1\""));
        assertTrue(payload.contains("xmlns:order=\"http://www.camel.apache.org/jaxb/example/order/1\""));
    }

};