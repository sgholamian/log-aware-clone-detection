//,temp,PaymentMethodGatewayIT.java,147,160,temp,PaymentMethodGatewayIT.java,132,145
//,3
public class xxx {
    @Test
    public void testDelete() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");
        assertNotNull(this.customer, "Customer can't be null");

        final PaymentMethod paymentMethod = createPaymentMethod();
        final Result<PaymentMethod> deleteResult = requestBody(
                "direct://DELETE", paymentMethod.getToken(), Result.class);

        assertNotNull(deleteResult, "create result");
        assertTrue(deleteResult.isSuccess());

        LOG.info("PaymentMethod deleted - token={}", paymentMethod.getToken());
    }

};