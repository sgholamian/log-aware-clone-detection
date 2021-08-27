//,temp,ServiceInterfaceStrategyTest.java,66,89,temp,ServiceInterfaceStrategyTest.java,38,64
//,3
public class xxx {
    @Test
    public void testServiceInterfaceStrategyWithClient() {
        ServiceInterfaceStrategy strategy = new ServiceInterfaceStrategy(CustomerService.class, true);
        QName elName = strategy.findQNameForSoapActionOrType("", GetCustomersByName.class);
        assertEquals("http://customerservice.example.com/", elName.getNamespaceURI());
        assertEquals("getCustomersByName", elName.getLocalPart());

        QName elName2 = strategy.findQNameForSoapActionOrType("getCustomersByName", GetCustomersByName.class);
        assertEquals("http://customerservice.example.com/", elName2.getNamespaceURI());
        assertEquals("getCustomersByName", elName2.getLocalPart());

        // Tests the case where the soap action is found but the in type is null
        QName elName3 = strategy.findQNameForSoapActionOrType("http://customerservice.example.com/getAllCustomers",
                null);
        assertNull(elName3);

        QName elName4 = strategy.findQNameForSoapActionOrType("http://customerservice.example.com/getAllAmericanCustomers",
                null);
        assertNull(elName4);

        try {
            elName = strategy.findQNameForSoapActionOrType("test", Class.class);
            fail();
        } catch (RuntimeCamelException e) {
            LOG.debug("Caught expected message: " + e.getMessage());
        }
    }

};