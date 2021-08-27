//,temp,sample_2396.java,2,19,temp,sample_2397.java,2,17
//,3
public class xxx {
public void dummy_method(){
Message found = null;
for (int i = 1; i <= count; ++i) {
Message msg = folder.getMessage(i);
if (uid.equals(getEndpoint().getMailUidGenerator().generateUuid(getEndpoint(), msg))) {
found = msg;
break;
}
}
if (found == null) {
boolean delete = getEndpoint().getConfiguration().isDelete();


log.info("not found in folder message cannot be marked as DELETED SEEN");
}
}

};