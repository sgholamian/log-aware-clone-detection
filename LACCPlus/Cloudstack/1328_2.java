//,temp,VirtualMachineMO.java,2501,2551,temp,VirtualMachineMO.java,2417,2498
//,3
public class xxx {
    public Pair<VirtualDisk, String> getDiskDevice(String vmdkDatastorePath) throws Exception {
        final String zeroLengthString = "";

        List<VirtualDevice> devices = _context.getVimClient().getDynamicProperty(_mor, "config.hardware.device");
        ArrayList<Pair<VirtualDisk, String>> partialMatchingDiskDevices = new ArrayList<>();

        DatastoreFile dsSrcFile = new DatastoreFile(vmdkDatastorePath);

        String srcBaseName = dsSrcFile.getFileBaseName();
        String trimmedSrcBaseName = VmwareHelper.trimSnapshotDeltaPostfix(srcBaseName);
        String srcDatastoreName = dsSrcFile.getDatastoreName() != null ? dsSrcFile.getDatastoreName() : zeroLengthString;

        s_logger.info("Look for disk device info for volume : " + vmdkDatastorePath + " with base name: " + srcBaseName);

        if (devices != null && devices.size() > 0) {
            for (VirtualDevice device : devices) {
                if (device instanceof VirtualDisk) {
                    s_logger.info("Test against disk device, controller key: " + device.getControllerKey() + ", unit number: " + device.getUnitNumber());

                    VirtualDeviceBackingInfo backingInfo = device.getBacking();

                    if (backingInfo instanceof VirtualDiskFlatVer2BackingInfo) {
                        VirtualDiskFlatVer2BackingInfo diskBackingInfo = (VirtualDiskFlatVer2BackingInfo)backingInfo;

                        do {
                            s_logger.info("Test against disk backing : " + diskBackingInfo.getFileName());

                            DatastoreFile dsBackingFile = new DatastoreFile(diskBackingInfo.getFileName());

                            String backingDatastoreName = dsBackingFile.getDatastoreName() != null ? dsBackingFile.getDatastoreName() : zeroLengthString;

                            if (srcDatastoreName.equals(zeroLengthString)) {
                                backingDatastoreName = zeroLengthString;
                            }

                            if (srcDatastoreName.equalsIgnoreCase(backingDatastoreName)) {
                                String backingBaseName = dsBackingFile.getFileBaseName();

                                if (backingBaseName.equalsIgnoreCase(srcBaseName)) {
                                    String deviceNumbering = getDeviceBusName(devices, device);

                                    s_logger.info("Disk backing : " + diskBackingInfo.getFileName() + " matches ==> " + deviceNumbering);

                                    return new Pair<>((VirtualDisk)device, deviceNumbering);
                                }

                                if (backingBaseName.contains(trimmedSrcBaseName)) {
                                    String deviceNumbering = getDeviceBusName(devices, device);

                                    partialMatchingDiskDevices.add(new Pair<>((VirtualDisk)device, deviceNumbering));
                                }
                            }

                            diskBackingInfo = diskBackingInfo.getParent();
                        } while (diskBackingInfo != null);
                    }
                }
            }
        }

        // No disk device was found with an exact match for the volume path, hence look for disk device that matches the trimmed name.
        s_logger.info("No disk device with an exact match found for volume : " + vmdkDatastorePath + ". Look for disk device info against trimmed base name: " + srcBaseName);

        if (partialMatchingDiskDevices != null) {
            if (partialMatchingDiskDevices.size() == 1) {
                VirtualDiskFlatVer2BackingInfo matchingDiskBackingInfo = (VirtualDiskFlatVer2BackingInfo)partialMatchingDiskDevices.get(0).first().getBacking();

                s_logger.info("Disk backing : " + matchingDiskBackingInfo.getFileName() + " matches ==> " + partialMatchingDiskDevices.get(0).second());

                return partialMatchingDiskDevices.get(0);
            } else if (partialMatchingDiskDevices.size() > 1) {
                s_logger.warn("Disk device info lookup for volume: " + vmdkDatastorePath + " failed as multiple disk devices were found to match" +
                        " volume's trimmed base name: " + trimmedSrcBaseName);

                return null;
            }
        }

        s_logger.warn("Disk device info lookup for volume: " + vmdkDatastorePath + " failed as no matching disk device found");

        return null;
    }

};