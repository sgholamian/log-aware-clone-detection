//,temp,Ovm3StorageProcessor.java,726,761,temp,Ovm3StorageProcessor.java,343,385
//,3
public class xxx {
    @Override
    public CopyCmdAnswer backupSnapshot(CopyCommand cmd) {
        LOGGER.debug("execute backupSnapshot: "+ cmd.getClass());
        try {
            DataTO srcData = cmd.getSrcTO();
            DataTO destData = cmd.getDestTO();
            SnapshotObjectTO src = (SnapshotObjectTO) srcData;
            SnapshotObjectTO dest = (SnapshotObjectTO) destData;

            // src.getPath contains the uuid of the snapshot.
            String srcFile = getVirtualDiskPath(src.getPath(), src.getDataStore().getUuid());

            // destination
            String storeUrl = dest.getDataStore().getUrl();
            String secPoolUuid = pool.setupSecondaryStorage(storeUrl);
            String destDir = config.getAgentSecStoragePath()
                    + "/" + secPoolUuid + "/"
                    + dest.getPath();
            String destFile =  destDir + "/" + src.getPath();
            destFile = destFile.concat(".raw");
            // copy
            Linux host = new Linux(c);
            CloudstackPlugin csp = new CloudstackPlugin(c);
            csp.ovsMkdirs(destDir);
            LOGGER.debug("CopyFrom: " + srcData.getObjectType() + ","
                    + srcFile + " to " + destData.getObjectType() + ","
                    + destFile);
            host.copyFile(srcFile, destFile);
            StoragePlugin sp = new StoragePlugin(c);
            sp.storagePluginDestroy(secPoolUuid, srcFile);

            SnapshotObjectTO newSnap = new SnapshotObjectTO();
            // newSnap.setPath(destFile);
            // damnit frickin crap, no reference whatsoever... could use parent ?
            newSnap.setPath(dest.getPath() + "/" + src.getPath() + ".raw");
            newSnap.setParentSnapshotPath(null);
            return new CopyCmdAnswer(newSnap);
        } catch (Ovm3ResourceException e) {
            String msg = "Error backupSnapshot: " + e.getMessage();
            LOGGER.info(msg);
            return new CopyCmdAnswer(msg);
        }
    }

};