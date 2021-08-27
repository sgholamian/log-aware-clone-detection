//,temp,AddressGatewayIT.java,118,140,temp,TransactionGatewayIT.java,240,273
//,3
public class xxx {
    @Test
    public void testCreate() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");
        assertNotNull(this.customer, "Customer can't be null");

        final Result<Address> address = requestBodyAndHeaders(
                "direct://CREATE",
                null,
                new BraintreeHeaderBuilder()
                        .add("customerId", customer.getId())
                        .add("request", new AddressRequest()
                                .company("Apache")
                                .streetAddress("1901 Munsey Drive")
                                .locality("Forest Hill"))
                        .build(),
                Result.class);

        assertNotNull(address, "create");
        assertTrue(address.isSuccess());

        LOG.info("Address created - customer={}, id={}", customer.getId(), address.getTarget().getId());
        this.addressIds.add(address.getTarget().getId());
    }

};