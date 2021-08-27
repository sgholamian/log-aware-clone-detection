//,temp,sample_3216.java,2,10,temp,sample_3214.java,2,10
//,2
public class xxx {
private void restoreDroplet(Exchange exchange) throws Exception {
if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.IMAGE_ID))) {
throw new IllegalArgumentException(DigitalOceanHeaders.IMAGE_ID + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().restoreDroplet(dropletId, exchange.getIn().getHeader(DigitalOceanHeaders.IMAGE_ID, Integer.class));


log.info("dropletaction restore");
}

};