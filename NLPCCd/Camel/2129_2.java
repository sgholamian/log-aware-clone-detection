//,temp,sample_5477.java,2,11,temp,sample_1847.java,2,11
//,2
public class xxx {
private void deleteTag(Exchange exchange) throws Exception {
String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);
if (ObjectHelper.isEmpty(name)) {
throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
}
Delete delete = getEndpoint().getDigitalOceanClient().deleteTag(name);


log.info("delete tag");
}

};