//,temp,sample_9609.java,2,18,temp,sample_9612.java,2,18
//,3
public class xxx {
public void dummy_method(){
Path absolutePath = makeAbsolute(f);
String key = pathToKey(absolutePath);
if (key.length() == 0) {
return newDirectory(absolutePath);
}
if(LOG.isDebugEnabled()) {
}
FileMetadata meta = store.retrieveMetadata(key);
if (meta != null) {
if(LOG.isDebugEnabled()) {


log.info("getfilestatus returning file for key");
}
}
}

};