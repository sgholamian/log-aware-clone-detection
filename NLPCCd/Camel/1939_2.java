//,temp,sample_2814.java,2,10,temp,sample_3509.java,2,10
//,2
public class xxx {
protected String getSchemaResourceUri(Message message) {
String schemaResourceUri = message.getHeader(XmlSignatureConstants.HEADER_SCHEMA_RESOURCE_URI, String.class);
if (schemaResourceUri == null) {
schemaResourceUri = getConfiguration().getSchemaResourceUri();
}


log.info("schema resource uri");
}

};