//,temp,VirtualMachineMO.java,2501,2551,temp,VirtualMachineMO.java,2417,2498
//,3
public class xxx {
    public Pair<VirtualDisk, String> getDiskDevice(String vmdkDatastorePath, boolean matchExactly) throws Exception {
        List<VirtualDevice> devices = _context.getVimClient().getDynamicProperty(_mor, "config.hardware.device");

        DatastoreFile dsSrcFile = new DatastoreFile(vmdkDatastorePath);
        String srcBaseName = dsSrcFile.getFileBaseName();
        String trimmedSrcBaseName = VmwareHelper.trimSnapshotDeltaPostfix(srcBaseName);

        if (matchExactly) {
            s_logger.info("Look for disk device info from volume : " + vmdkDatastorePath + " with base name: " + srcBaseName);
        } else {
            s_logger.info("Look for disk device info from volume : " + vmdkDatastorePath + " with trimmed base name: " + trimmedSrcBaseName);
        }

        if (devices != null && devices.size() > 0) {
            for (VirtualDevice device : devices) {
                if (device instanceof VirtualDisk) {
                    s_logger.info("Test against disk device, controller key: " + device.getControllerKey() + ", unit number: " + device.getUnitNumber());

                    VirtualDeviceBackingInfo backingInfo = ((VirtualDisk)device).getBacking();
                    if (backingInfo instanceof VirtualDiskFlatVer2BackingInfo) {
                        VirtualDiskFlatVer2BackingInfo diskBackingInfo = (VirtualDiskFlatVer2BackingInfo)backingInfo;
                        do {
                            s_logger.info("Test against disk backing : " + diskBackingInfo.getFileName());

                            DatastoreFile dsBackingFile = new DatastoreFile(diskBackingInfo.getFileName());
                            String backingBaseName = dsBackingFile.getFileBaseName();
                            if (matchExactly) {
                                if (backingBaseName.equalsIgnoreCase(srcBaseName)) {
                                    String deviceNumbering = getDeviceBusName(devices, device);

                                    s_logger.info("Disk backing : " + diskBackingInfo.getFileName() + " matches ==> " + deviceNumbering);
                                    return new Pair<VirtualDisk, String>((VirtualDisk)device, deviceNumbering);
                                }
                            } else {
                                if (backingBaseName.contains(trimmedSrcBaseName)) {
                                    String deviceNumbering = getDeviceBusName(devices, device);

                                    s_logger.info("Disk backing : " + diskBackingInfo.getFileName() + " matches ==> " + deviceNumbering);
                                    return new Pair<VirtualDisk, String>((VirtualDisk)device, deviceNumbering);
                                }
                            }

                            diskBackingInfo = diskBackingInfo.getParent();
                        } while (diskBackingInfo != null);
                    }
                }
            }
        }

        return null;
    }

};