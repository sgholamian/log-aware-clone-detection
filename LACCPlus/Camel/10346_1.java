//,temp,AddressGatewayIT.java,61,73,temp,PaymentMethodGatewayIT.java,60,72
//,2
public class xxx {
    @Override
    protected void doPostSetup() throws Exception {
        this.gateway = getGateway();
        this.customer = gateway.customer().create(
                new CustomerRequest()
                        .firstName("user")
                        .lastName(UUID.randomUUID().toString()))
                .getTarget();

        if (customer != null) {
            LOG.info("Customer created - id={}", this.customer.getId());
        }
    }

};