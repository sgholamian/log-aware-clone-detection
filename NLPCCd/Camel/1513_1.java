//,temp,sample_8063.java,2,19,temp,sample_8058.java,2,17
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < mp.getCount(); i++) {
Part part = mp.getBodyPart(i);
if (part.isMimeType("multipart/*")) {
extractAttachmentsFromMultipart((Multipart) part.getContent(), map);
} else {
String disposition = part.getDisposition();
String fileName = part.getFileName();
if (LOG.isTraceEnabled()) {
}
if (validDisposition(disposition, fileName) || fileName != null) {


log.info("mail contains file attachment");
}
}
}
}

};