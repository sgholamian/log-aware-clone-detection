//,temp,PaymentMethodGatewayIT.java,147,160,temp,PaymentMethodGatewayIT.java,132,145
//,3
public class xxx {
    @Test
    public void testFind() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");
        assertNotNull(this.customer, "Customer can't be null");

        final PaymentMethod paymentMethod = createPaymentMethod();
        this.paymentMethodsTokens.add(paymentMethod.getToken());

        final PaymentMethod method = requestBody(
                "direct://FIND", paymentMethod.getToken(), PaymentMethod.class);

        assertNotNull(method, "find result");
        LOG.info("PaymentMethod found - token={}", method.getToken());
    }

};