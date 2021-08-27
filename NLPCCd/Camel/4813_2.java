//,temp,sample_8067.java,2,13,temp,sample_8068.java,2,13
//,2
public class xxx {
protected void addAttachmentsToMultipart(MimeMultipart multipart, String partDisposition, AttachmentsContentTransferEncodingResolver encodingResolver, Exchange exchange) throws MessagingException {
int i = 0;
for (Map.Entry<String, Attachment> entry : exchange.getIn().getAttachmentObjects().entrySet()) {
String attachmentFilename = entry.getKey();
Attachment attachment = entry.getValue();
if (LOG.isTraceEnabled()) {


log.info("attachment filename");
}
}
}

};