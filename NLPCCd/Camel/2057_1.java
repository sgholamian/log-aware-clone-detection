//,temp,sample_8060.java,2,17,temp,sample_8061.java,2,17
//,2
public class xxx {
protected void extractAttachmentsFromMultipart(Multipart mp, Map<String, Attachment> map) throws MessagingException, IOException {
for (int i = 0; i < mp.getCount(); i++) {
Part part = mp.getBodyPart(i);
if (part.isMimeType("multipart/*")) {
extractAttachmentsFromMultipart((Multipart) part.getContent(), map);
} else {
String disposition = part.getDisposition();
String fileName = part.getFileName();
if (LOG.isTraceEnabled()) {


log.info("part filename");
}
}
}
}

};