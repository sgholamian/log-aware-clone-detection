//,temp,sample_2818.java,2,10,temp,sample_2819.java,2,10
//,2
public class xxx {
protected String getMessageEncoding(Message inMessage) {
String encoding = inMessage.getHeader(XmlSignatureConstants.HEADER_PLAIN_TEXT_ENCODING, String.class);
if (encoding == null) {
encoding = getConfiguration().getPlainTextEncoding();
}


log.info("messge encoding");
}

};