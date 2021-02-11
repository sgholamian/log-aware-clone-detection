//,temp,sample_2943.java,2,20,temp,sample_2939.java,2,15
//,3
public class xxx {
public void dummy_method(){
Object regionData = visitor.regionOpen(regionInfo);
monitor.rethrowException();
Collection<String> familyNames = regionFs.getFamilies();
if (familyNames != null) {
for (String familyName: familyNames) {
Object familyData = visitor.familyOpen(regionData, Bytes.toBytes(familyName));
monitor.rethrowException();
Collection<StoreFileInfo> storeFiles = regionFs.getStoreFiles(familyName);
if (storeFiles == null) {
if (LOG.isDebugEnabled()) {


log.info("no files under family");
}
}
}
}
}

};