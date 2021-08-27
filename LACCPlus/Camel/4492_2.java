//,temp,AddressGatewayIT.java,183,207,temp,AddressGatewayIT.java,163,181
//,3
public class xxx {
    @Test
    public void testFind() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");
        assertNotNull(this.customer, "Customer can't be null");

        final Address addressRef = createAddress();
        this.addressIds.add(addressRef.getId());

        final Address address = requestBodyAndHeaders(
                "direct://FIND", null,
                new BraintreeHeaderBuilder()
                        .add("customerId", customer.getId())
                        .add("id", addressRef.getId())
                        .build(),
                Address.class);

        assertNotNull(address, "find");
        LOG.info("Address found - customer={}, id={}", customer.getId(), address.getId());
    }

};