//,temp,PaymentMethodGatewayIT.java,114,130,temp,PaymentMethodGatewayIT.java,96,108
//,3
public class xxx {
    private PaymentMethod createPaymentMethod() {
        Result<? extends PaymentMethod> result = this.gateway.paymentMethod().create(
                new PaymentMethodRequest()
                        .customerId(this.customer.getId())
                        .paymentMethodNonce("fake-valid-payroll-nonce"));

        assertNotNull(result, "create result");
        assertTrue(result.isSuccess());

        LOG.info("PaymentMethod created - token={}", result.getTarget().getToken());

        return result.getTarget();
    }

};