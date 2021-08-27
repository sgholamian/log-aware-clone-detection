//,temp,sample_8049.java,2,9,temp,sample_8048.java,2,7
//,3
public class xxx {
protected String populateContentOnBodyPart(BodyPart part, MailConfiguration configuration, Exchange exchange) throws MessagingException, IOException {
String contentType = determineContentType(configuration, exchange);
if (contentType != null) {


log.info("using content type for bodypart");
}
}

};