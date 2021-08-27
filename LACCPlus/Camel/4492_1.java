//,temp,AddressGatewayIT.java,183,207,temp,AddressGatewayIT.java,163,181
//,3
public class xxx {
    @Test
    public void testUpdate() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");
        assertNotNull(this.customer, "Customer can't be null");

        final Address addressRef = createAddress();
        this.addressIds.add(addressRef.getId());

        final Result<Address> result = requestBodyAndHeaders(
                "direct://UPDATE", null,
                new BraintreeHeaderBuilder()
                        .add("customerId", customer.getId())
                        .add("id", addressRef.getId())
                        .add("request", new AddressRequest()
                                .company("Apache")
                                .streetAddress(customer.getId())
                                .locality(customer.getId()))
                        .build(),
                Result.class);

        assertNotNull(result, "update");
        assertTrue(result.isSuccess());

        LOG.info("Address updated - customer={}, id={}", customer.getId(), result.getTarget().getId());
    }

};