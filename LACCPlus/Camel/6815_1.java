//,temp,AddressGatewayIT.java,75,95,temp,PaymentMethodGatewayIT.java,74,94
//,3
public class xxx {
    @Override
    @AfterEach
    public void tearDown() throws Exception {
        if (this.gateway != null && customer != null) {
            for (String id : this.addressIds) {
                if (this.gateway.address().delete(customer.getId(), id).isSuccess()) {
                    LOG.info("Address deleted - customer={}, id={}", customer.getId(), id);
                } else {
                    LOG.warn("Unable to delete address - customer={}, id={}", customer.getId(), id);
                }
            }

            this.addressIds.clear();

            if (this.gateway.customer().delete(this.customer.getId()).isSuccess()) {
                LOG.info("Customer deleted - id={}", this.customer.getId());
            } else {
                LOG.warn("Unable to delete customer - id={}", this.customer.getId());
            }
        }
    }

};