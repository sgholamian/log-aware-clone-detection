//,temp,sample_3121.java,2,19,temp,sample_3123.java,2,19
//,2
public class xxx {
public void dummy_method(){
DatacenterMO dcMo = getOwnerDatacenter().first();
if (disks != null) {
for (VirtualDevice disk : disks) {
List<Pair<String, ManagedObjectReference>> vmdkFiles = getDiskDatastorePathChain((VirtualDisk)disk, followDiskChain);
for (Pair<String, ManagedObjectReference> fileItem : vmdkFiles) {
DatastoreMO srcDsMo = new DatastoreMO(_context, fileItem.second());
DatastoreFile srcFile = new DatastoreFile(fileItem.first());
DatastoreFile destFile = new DatastoreFile(destDsMo.getName(), destDsDir, srcFile.getFileName());
Pair<VmdkFileDescriptor, byte[]> vmdkDescriptor = null;
vmdkDescriptor = getVmdkFileInfo(fileItem.first());


log.info("move vm disk file to");
}
}
}
}

};