//,temp,sample_1845.java,2,11,temp,sample_4742.java,2,11
//,2
public class xxx {
private void getTag(Exchange exchange) throws Exception {
String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);
if (ObjectHelper.isEmpty(name)) {
throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
}
Tag tag = getEndpoint().getDigitalOceanClient().getTag(name);


log.info("tag");
}

};