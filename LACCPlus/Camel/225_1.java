//,temp,DigitalOceanDropletsProducer.java,340,349,temp,DigitalOceanDropletsProducer.java,329,338
//,2
public class xxx {
    private void changeDropletKernel(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.KERNEL_ID))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.KERNEL_ID + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().changeDropletKernel(dropletId,
                exchange.getIn().getHeader(DigitalOceanHeaders.KERNEL_ID, Integer.class));
        LOG.trace("Change Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};