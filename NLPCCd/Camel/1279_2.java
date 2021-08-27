//,temp,sample_3217.java,2,10,temp,sample_3230.java,2,12
//,3
public class xxx {
private void tagDroplet(Exchange exchange) throws Exception {
if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
}
ArrayList<Resource> resources = new ArrayList<>(1);
resources.add(new Resource(dropletId.toString(), ResourceType.DROPLET));
Response response = getEndpoint().getDigitalOceanClient().tagResources(dropletId.toString(), resources);


log.info("tag droplet");
}

};