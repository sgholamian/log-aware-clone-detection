//,temp,LibvirtStorageAdaptor.java,1331,1361,temp,LibvirtStorageAdaptor.java,98,123
//,3
public class xxx {
    @Override
    public KVMPhysicalDisk createDiskFromTemplateBacking(KVMPhysicalDisk template, String name, PhysicalDiskFormat format, long size,
                                                         KVMStoragePool destPool, int timeout) {
        s_logger.info("Creating volume " + name + " with template backing " + template.getName() + " in pool " + destPool.getUuid() +
                " (" + destPool.getType().toString() + ") with size " + size);

        KVMPhysicalDisk disk = null;
        String destPath = destPool.getLocalPath().endsWith("/") ?
                destPool.getLocalPath() + name :
                destPool.getLocalPath() + "/" + name;

        if (destPool.getType() == StoragePoolType.NetworkFilesystem) {
            try {
                if (format == PhysicalDiskFormat.QCOW2) {
                    QemuImg qemu = new QemuImg(timeout);
                    QemuImgFile destFile = new QemuImgFile(destPath, format);
                    destFile.setSize(size);
                    QemuImgFile backingFile = new QemuImgFile(template.getPath(), template.getFormat());
                    qemu.create(destFile, backingFile);
                }
            } catch (QemuImgException e) {
                s_logger.error("Failed to create " + destPath + " due to a failed executing of qemu-img: " + e.getMessage());
            }
        }
        return disk;
    }

};