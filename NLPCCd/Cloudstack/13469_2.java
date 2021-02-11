//,temp,sample_474.java,2,13,temp,sample_475.java,2,14
//,3
public class xxx {
public boolean folderExists(String folderParentDatastorePath, String folderName) throws Exception {
HostDatastoreBrowserMO browserMo = getHostDatastoreBrowserMO();
HostDatastoreBrowserSearchResults results = browserMo.searchDatastore(folderParentDatastorePath, folderName, true);
if (results != null) {
List<FileInfo> info = results.getFile();
if (info != null && info.size() > 0) {
return true;
}
}


log.info("folder does not exist on datastore");
}

};