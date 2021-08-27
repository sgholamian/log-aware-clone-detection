//,temp,sample_2399.java,2,18,temp,sample_2400.java,2,17
//,3
public class xxx {
public void dummy_method(){
MailConfiguration config = getEndpoint().getConfiguration();
String copyTo = in.getHeader("copyTo", config.getCopyTo(), String.class);
boolean delete = in.getHeader("delete", config.isDelete(), boolean.class);
if (config.getProtocol().equals(MailUtils.PROTOCOL_IMAP) || config.getProtocol().equals(MailUtils.PROTOCOL_IMAPS)) {
if (copyTo != null) {
Folder destFolder = store.getFolder(copyTo);
if (!destFolder.exists()) {
destFolder.create(Folder.HOLDS_MESSAGES);
}
folder.copyMessages(new Message[]{mail}, destFolder);


log.info("imap message copied to");
}
}
}

};