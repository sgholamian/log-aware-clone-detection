//,temp,sample_3217.java,2,10,temp,sample_3230.java,2,12
//,3
public class xxx {
private void renameDroplet(Exchange exchange) throws Exception {
if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().renameDroplet(dropletId, exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class));


log.info("rename droplet");
}

};