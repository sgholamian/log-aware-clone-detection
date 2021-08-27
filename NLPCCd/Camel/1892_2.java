//,temp,sample_8053.java,2,11,temp,sample_8054.java,2,11
//,2
public class xxx {
public void extractAttachmentsFromMail(Message message, Map<String, Attachment> map) throws MessagingException, IOException {
Object content = message.getContent();
if (content instanceof Multipart) {
extractAttachmentsFromMultipart((Multipart) content, map);
} else if (content != null) {
}


log.info("extracting attachments done");
}

};