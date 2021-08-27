//,temp,sample_3215.java,2,10,temp,sample_3218.java,2,10
//,2
public class xxx {
private void resizeDroplet(Exchange exchange) throws Exception {
if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_SIZE))) {
throw new IllegalArgumentException(DigitalOceanHeaders.DROPLET_SIZE + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().resizeDroplet(dropletId, exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_SIZE, String.class));


log.info("dropletaction resize");
}

};