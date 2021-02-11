//,temp,sample_5358.java,2,17,temp,sample_5357.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (fileFullPath != null) {
dsMo.deleteFile(fileFullPath, dcMo.getMor(), true);
} else {
}
fileName = volumeName + "-delta.vmdk";
fileFullPath = getLegacyDatastorePathFromVmdkFileName(dsMo, fileName);
if (!dsMo.fileExists(fileFullPath)) fileFullPath = dsMo.searchFileInSubFolders(fileName, false);
if (fileFullPath != null) {
dsMo.deleteFile(fileFullPath, dcMo.getMor(), true);
} else {


log.info("unable to locate vmdk file");
}
}

};