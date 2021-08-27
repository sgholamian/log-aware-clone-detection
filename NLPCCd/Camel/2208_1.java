//,temp,sample_8400.java,2,12,temp,sample_7217.java,2,12
//,2
public class xxx {
protected void onExchange(Exchange exchange) throws Exception {
String path = getResourceUri();
ObjectHelper.notNull(path, "resourceUri");
String newResourceUri = exchange.getIn().getHeader(JoltConstants.JOLT_RESOURCE_URI, String.class);
if (newResourceUri != null) {
exchange.getIn().removeHeader(JoltConstants.JOLT_RESOURCE_URI);


log.info("set to creating new endpoint to handle exchange");
}
}

};