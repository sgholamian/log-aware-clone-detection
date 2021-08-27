//,temp,sample_2818.java,2,10,temp,sample_2819.java,2,10
//,2
public class xxx {
protected String getContentReferenceType(Message message) {
String type = message.getHeader(XmlSignatureConstants.HEADER_CONTENT_REFERENCE_TYPE, String.class);
if (type == null) {
type = getConfiguration().getContentReferenceType();
}


log.info("content reference type");
}

};