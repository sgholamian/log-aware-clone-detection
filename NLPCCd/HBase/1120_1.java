//,temp,sample_198.java,2,12,temp,sample_543.java,2,13
//,3
public class xxx {
public Collection<StoreFileInfo> getStoreFiles(final String familyName, final boolean validate) throws IOException {
Path familyDir = getStoreDir(familyName);
FileStatus[] files = FSUtils.listStatus(this.fs, familyDir);
if (files == null) {
if (LOG.isTraceEnabled()) {


log.info("no storefiles for");
}
}
}

};