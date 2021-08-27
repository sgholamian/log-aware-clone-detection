//,temp,sample_8056.java,2,11,temp,sample_8055.java,2,9
//,3
public class xxx {
protected void extractAttachmentsFromMultipart(Multipart mp, Map<String, Attachment> map) throws MessagingException, IOException {
for (int i = 0; i < mp.getCount(); i++) {
Part part = mp.getBodyPart(i);


log.info("part");
}
}

};