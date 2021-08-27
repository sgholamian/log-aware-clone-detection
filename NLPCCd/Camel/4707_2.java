//,temp,sample_3215.java,2,10,temp,sample_3218.java,2,10
//,2
public class xxx {
private void changeDropletKernel(Exchange exchange) throws Exception {
if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.KERNEL_ID))) {
throw new IllegalArgumentException(DigitalOceanHeaders.KERNEL_ID + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().changeDropletKernel(dropletId, exchange.getIn().getHeader(DigitalOceanHeaders.KERNEL_ID, Integer.class));


log.info("rename droplet");
}

};