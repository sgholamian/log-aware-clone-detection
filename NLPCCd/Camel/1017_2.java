//,temp,sample_5481.java,2,16,temp,sample_3229.java,2,12
//,3
public class xxx {
private void takeDropletSnapshot(Exchange exchange) throws Exception {
Action action;
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
action = getEndpoint().getDigitalOceanClient().takeDropletSnapshot(dropletId, exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class));
} else {
action = getEndpoint().getDigitalOceanClient().takeDropletSnapshot(dropletId);
}


log.info("take snapshot for droplet");
}

};