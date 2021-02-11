//,temp,sample_3272.java,2,18,temp,sample_7153.java,2,18
//,3
public class xxx {
public void dummy_method(){
while (iter.hasNext()) {
Entry<byte[], byte[]> entry = iter.peekNext();
String key = asString(entry.getKey());
if (!key.startsWith(AMRMPROXY_KEY_PREFIX)) {
break;
}
String suffix = key.substring(AMRMPROXY_KEY_PREFIX.length());
if (suffix.equals(CURRENT_MASTER_KEY_SUFFIX)) {
iter.next();
result.setCurrentMasterKey(parseMasterKey(entry.getValue()));


log.info("recovered for amrmproxy current master key id");
}
}
}

};