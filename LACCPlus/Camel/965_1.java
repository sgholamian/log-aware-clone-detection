//,temp,ServiceInterfaceStrategyTest.java,66,89,temp,ServiceInterfaceStrategyTest.java,38,64
//,3
public class xxx {
    @Test
    public void testServiceInterfaceStrategyWithServer() {
        ServiceInterfaceStrategy strategy = new ServiceInterfaceStrategy(CustomerService.class, false);

        // Tests the case where the action is not found but the type is
        QName elName = strategy.findQNameForSoapActionOrType("", GetCustomersByNameResponse.class);
        assertEquals("http://customerservice.example.com/", elName.getNamespaceURI());
        assertEquals("getCustomersByNameResponse", elName.getLocalPart());

        // Tests the case where the soap action is found
        QName elName2 = strategy.findQNameForSoapActionOrType("http://customerservice.example.com/getCustomersByName",
                GetCustomersByName.class);
        assertEquals("http://customerservice.example.com/", elName2.getNamespaceURI());
        assertEquals("getCustomersByNameResponse", elName2.getLocalPart());

        // this tests the case that the soap action as well as the type are not
        // found
        try {
            elName = strategy.findQNameForSoapActionOrType("test", Class.class);
            fail();
        } catch (RuntimeCamelException e) {
            LOG.debug("Caught expected message: " + e.getMessage());
        }
    }

};