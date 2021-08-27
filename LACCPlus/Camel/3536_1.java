//,temp,PaymentMethodGatewayIT.java,114,130,temp,PaymentMethodGatewayIT.java,96,108
//,3
public class xxx {
    @Test
    public void testCreate() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");
        assertNotNull(this.customer, "Customer can't be null");

        final Result<PaymentMethod> result = requestBody("direct://CREATE",
                new PaymentMethodRequest()
                        .customerId(this.customer.getId())
                        .paymentMethodNonce("fake-valid-payroll-nonce"),
                Result.class);

        assertNotNull(result, "create result");
        assertTrue(result.isSuccess());

        LOG.info("PaymentMethod created - token={}", result.getTarget().getToken());
        this.paymentMethodsTokens.add(result.getTarget().getToken());
    }

};