//,temp,JaxbDataFormatSchemaValidationTest.java,47,76,temp,JaxbDataFormatPartClassTest.java,39,66
//,3
public class xxx {
    @Test
    public void testMarshallSuccess() throws Exception {
        mockMarshall.expectedMessageCount(1);

        Address address = new Address();
        address.setAddressLine1("Hauptstr. 1; 01129 Entenhausen");
        Person person = new Person();
        person.setFirstName("Christian");
        person.setLastName("Mueller");
        person.setAge(Integer.valueOf(36));
        person.setAddress(address);

        template.sendBody("direct:marshall", person);

        assertMockEndpointsSatisfied();

        String payload = mockMarshall.getExchanges().get(0).getIn().getBody(String.class);
        LOG.info(payload);

        assertTrue(payload.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
        assertTrue(payload.contains(
                "<person xmlns=\"person.jaxb.converter.camel.apache.org\" xmlns:ns2=\"address.jaxb.converter.camel.apache.org\">"));
        assertTrue(payload.contains("<firstName>Christian</firstName>"));
        assertTrue(payload.contains("<lastName>Mueller</lastName>"));
        assertTrue(payload.contains("<age>36</age>"));
        assertTrue(payload.contains("<address>"));
        assertTrue(payload.contains("<ns2:addressLine1>Hauptstr. 1; 01129 Entenhausen</ns2:addressLine1>"));
        assertTrue(payload.contains("</address>"));
        assertTrue(payload.contains("</person>"));
    }

};