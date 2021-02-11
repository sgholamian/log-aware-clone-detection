//,temp,VirtualMachineMO.java,2117,2147,temp,VirtualMachineMO.java,2062,2092
//,3
public class xxx {
    public void copyAllVmDiskFiles(DatastoreMO destDsMo, String destDsDir, boolean followDiskChain) throws Exception {
        VirtualDevice[] disks = getAllDiskDevice();
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

                    s_logger.info("Copy VM disk file " + srcFile.getPath() + " to " + destFile.getPath());
                    srcDsMo.copyDatastoreFile(fileItem.first(), dcMo.getMor(), destDsMo.getMor(), destFile.getPath(), dcMo.getMor(), true);

                    if (vmdkDescriptor != null) {
                        String vmdkBaseFileName = vmdkDescriptor.first().getBaseFileName();
                        String baseFilePath = srcFile.getCompanionPath(vmdkBaseFileName);
                        destFile = new DatastoreFile(destDsMo.getName(), destDsDir, vmdkBaseFileName);

                        s_logger.info("Copy VM disk file " + baseFilePath + " to " + destFile.getPath());
                        srcDsMo.copyDatastoreFile(baseFilePath, dcMo.getMor(), destDsMo.getMor(), destFile.getPath(), dcMo.getMor(), true);
                    }
                }
            }
        }
    }

};