//,temp,sample_2399.java,2,18,temp,sample_2400.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (config.getProtocol().equals(MailUtils.PROTOCOL_IMAP) || config.getProtocol().equals(MailUtils.PROTOCOL_IMAPS)) {
if (copyTo != null) {
Folder destFolder = store.getFolder(copyTo);
if (!destFolder.exists()) {
destFolder.create(Folder.HOLDS_MESSAGES);
}
folder.copyMessages(new Message[]{mail}, destFolder);
}
}
if (delete) {


log.info("exchange processed so flagging message as deleted");
}
}

};