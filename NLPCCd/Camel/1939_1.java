//,temp,sample_2814.java,2,10,temp,sample_3509.java,2,10
//,2
public class xxx {
protected Boolean isPlainText(Message message) {
Boolean isPlainText = message.getHeader(XmlSignatureConstants.HEADER_MESSAGE_IS_PLAIN_TEXT, Boolean.class);
if (isPlainText == null) {
isPlainText = getConfiguration().getPlainText();
}


log.info("is plain text");
}

};