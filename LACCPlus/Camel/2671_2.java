//,temp,XmlSignerProcessor.java,672,679,temp,XmlSignerProcessor.java,459,466
//,2
public class xxx {
    protected Boolean isPlainText(Message message) {
        Boolean isPlainText = message.getHeader(XmlSignatureConstants.HEADER_MESSAGE_IS_PLAIN_TEXT, Boolean.class);
        if (isPlainText == null) {
            isPlainText = getConfiguration().getPlainText();
        }
        LOG.debug("Is plain text: {}", isPlainText);
        return isPlainText;
    }

};