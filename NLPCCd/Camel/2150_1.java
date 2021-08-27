//,temp,sample_1844.java,2,11,temp,sample_5487.java,2,11
//,2
public class xxx {
private void createTag(Exchange exchange) throws Exception {
String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);
if (ObjectHelper.isEmpty(name)) {
throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
}
Tag tag = getEndpoint().getDigitalOceanClient().createTag(name);


log.info("create tag");
}

};