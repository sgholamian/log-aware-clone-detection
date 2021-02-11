//,temp,sample_7149.java,2,18,temp,sample_7153.java,2,18
//,3
public class xxx {
public void dummy_method(){
iter = new LeveldbIterator(db);
iter.seek(bytes(RM_APP_KEY_PREFIX));
while (iter.hasNext()) {
Entry<byte[],byte[]> entry = iter.next();
String key = asString(entry.getKey());
if (!key.startsWith(RM_APP_KEY_PREFIX)) {
break;
}
String appIdStr = key.substring(RM_APP_ROOT.length() + 1);
if (appIdStr.contains(SEPARATOR)) {


log.info("skipping extraneous data");
}
}
}

};