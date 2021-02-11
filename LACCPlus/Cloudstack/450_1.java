//,temp,LibvirtStorageAdaptor.java,1331,1361,temp,LibvirtStorageAdaptor.java,98,123
//,3
public class xxx {
    @Override
    public KVMPhysicalDisk createDiskFromSnapshot(KVMPhysicalDisk snapshot, String snapshotName, String name, KVMStoragePool destPool, int timeout) {
        s_logger.info("Creating volume " + name + " from snapshot " + snapshotName + " in pool " + destPool.getUuid() +
                " (" + destPool.getType().toString() + ")");

        PhysicalDiskFormat format = snapshot.getFormat();
        long size = snapshot.getSize();
        String destPath = destPool.getLocalPath().endsWith("/") ?
                destPool.getLocalPath() + name :
                destPool.getLocalPath() + "/" + name;

        if (destPool.getType() == StoragePoolType.NetworkFilesystem) {
            try {
                if (format == PhysicalDiskFormat.QCOW2) {
                    QemuImg qemu = new QemuImg(timeout);
                    QemuImgFile destFile = new QemuImgFile(destPath, format);
                    if (size > snapshot.getVirtualSize()) {
                        destFile.setSize(size);
                    } else {
                        destFile.setSize(snapshot.getVirtualSize());
                    }
                    QemuImgFile srcFile = new QemuImgFile(snapshot.getPath(), snapshot.getFormat());
                    qemu.convert(srcFile, destFile, snapshotName);
                }
            } catch (QemuImgException e) {
                s_logger.error("Failed to create " + destPath +
                        " due to a failed executing of qemu-img: " + e.getMessage());
            }
        }
        return destPool.getPhysicalDisk(name);
    }

};