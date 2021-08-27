//,temp,XmlSignerProcessor.java,672,679,temp,XmlSignerProcessor.java,459,466
//,2
public class xxx {
    protected String getContentReferenceType(Message message) {
        String type = message.getHeader(XmlSignatureConstants.HEADER_CONTENT_REFERENCE_TYPE, String.class);
        if (type == null) {
            type = getConfiguration().getContentReferenceType();
        }
        LOG.debug("Content reference type: {}", type);
        return type;
    }

};