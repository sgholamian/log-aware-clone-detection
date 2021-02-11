//,temp,sample_3122.java,2,17,temp,sample_3124.java,2,17
//,2
public class xxx {
public void dummy_method(){
DatastoreMO srcDsMo = new DatastoreMO(_context, fileItem.second());
DatastoreFile srcFile = new DatastoreFile(fileItem.first());
DatastoreFile destFile = new DatastoreFile(destDsMo.getName(), destDsDir, srcFile.getFileName());
Pair<VmdkFileDescriptor, byte[]> vmdkDescriptor = null;
vmdkDescriptor = getVmdkFileInfo(fileItem.first());
srcDsMo.moveDatastoreFile(fileItem.first(), dcMo.getMor(), destDsMo.getMor(), destFile.getPath(), dcMo.getMor(), true);
if (vmdkDescriptor != null) {
String vmdkBaseFileName = vmdkDescriptor.first().getBaseFileName();
String baseFilePath = srcFile.getCompanionPath(vmdkBaseFileName);
destFile = new DatastoreFile(destDsMo.getName(), destDsDir, vmdkBaseFileName);


log.info("move vm disk file to");
}
}

};