//,temp,AddressGatewayIT.java,75,95,temp,PaymentMethodGatewayIT.java,74,94
//,3
public class xxx {
    @Override
    @AfterEach
    public void tearDown() throws Exception {
        if (this.gateway != null) {
            for (String token : this.paymentMethodsTokens) {
                if (this.gateway.paymentMethod().delete(token).isSuccess()) {
                    LOG.info("PaymentMethod deleted - token={}", token);
                } else {
                    LOG.warn("Unable to delete PaymentMethod - token={}", token);
                }
            }

            this.paymentMethodsTokens.clear();

            if (this.gateway.customer().delete(this.customer.getId()).isSuccess()) {
                LOG.info("Customer deleted - id={}", this.customer.getId());
            } else {
                LOG.warn("Unable to delete customer - id={}", this.customer.getId());
            }
        }
    }

};