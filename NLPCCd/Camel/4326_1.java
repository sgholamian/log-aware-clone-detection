//,temp,sample_2396.java,2,19,temp,sample_2397.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (!folder.isOpen()) {
folder.open(Folder.READ_WRITE);
}
String uid = (String) exchange.removeProperty(MAIL_MESSAGE_UID);
if (getEndpoint().getConfiguration().getProtocol().startsWith("pop3")) {
int count = folder.getMessageCount();
Message found = null;
for (int i = 1; i <= count; ++i) {
Message msg = folder.getMessage(i);
if (uid.equals(getEndpoint().getMailUidGenerator().generateUuid(getEndpoint(), msg))) {


log.info("found with uid from folder with mails");
}
}
}
}

};