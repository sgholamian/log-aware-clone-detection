//,temp,sample_5879.java,2,12,temp,sample_15.java,2,13
//,3
public class xxx {
protected void onExchange(Exchange exchange) throws Exception {
String path = getResourceUri();
ObjectHelper.notNull(configuration, "configuration");
ObjectHelper.notNull(path, "resourceUri");
String newResourceUri = exchange.getIn().getHeader(FreemarkerConstants.FREEMARKER_RESOURCE_URI, String.class);
if (newResourceUri != null) {
exchange.getIn().removeHeader(FreemarkerConstants.FREEMARKER_RESOURCE_URI);


log.info("set to creating new endpoint to handle exchange");
}
}

};