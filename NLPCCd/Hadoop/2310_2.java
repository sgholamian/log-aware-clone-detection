//,temp,sample_9609.java,2,18,temp,sample_9612.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (store.retrieveMetadata(key + FOLDER_SUFFIX) != null) {
if(LOG.isDebugEnabled()) {
}
return newDirectory(absolutePath);
}
if(LOG.isDebugEnabled()) {
}
PartialListing listing = store.list(key, 1);
if (listing.getFiles().length > 0 || listing.getCommonPrefixes().length > 0) {
if(LOG.isDebugEnabled()) {


log.info("getfilestatus returning directory for key as it has contents");
}
}
}

};