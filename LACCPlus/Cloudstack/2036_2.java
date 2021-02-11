//,temp,SiocManagerImpl.java,277,335,temp,SiocManagerImpl.java,177,263
//,3
public class xxx {
    private ResultWrapper updateSiocInfo(VMwareUtil.VMwareConnection connection, Map<String, ManagedObjectReference> nameToVm, Long instanceId,
                                         StoragePoolVO storagePool, int sharesPerGB, int limitIopsPerGB) throws Exception {
        int limitIopsTotal = 0;
        List<ManagedObjectReference> tasks = new ArrayList<>();

        VMInstanceVO vmInstance = vmInstanceDao.findById(instanceId);

        if (vmInstance == null) {
            String errMsg = "Error: The VM with ID " + instanceId + " could not be located.";

            throw new Exception(errMsg);
        }

        String vmName = vmInstance.getInstanceName();

        ManagedObjectReference morVm = nameToVm.get(vmName);

        if (morVm == null) {
            String errMsg = "Error: The VM with ID " + instanceId + " could not be located (ManagedObjectReference).";

            throw new Exception(errMsg);
        }

        VirtualMachineConfigInfo vmci = (VirtualMachineConfigInfo)VMwareUtil.getEntityProps(connection, morVm,
                new String[] { "config" }).get("config");
        List<VirtualDevice> devices = vmci.getHardware().getDevice();

        for (VirtualDevice device : devices) {
            if (device instanceof VirtualDisk) {
                VirtualDisk disk = (VirtualDisk)device;

                VolumeVO volumeVO = getVolumeFromVirtualDisk(vmInstance, storagePool.getId(), devices, disk);

                if (volumeVO != null) {
                    boolean diskUpdated = false;

                    StorageIOAllocationInfo sioai = disk.getStorageIOAllocation();

                    SharesInfo sharesInfo = sioai.getShares();

                    int currentShares = sharesInfo.getShares();
                    int newShares = getNewSharesBasedOnVolumeSize(volumeVO, sharesPerGB);

                    if (currentShares != newShares) {
                        sharesInfo.setLevel(SharesLevel.CUSTOM);
                        sharesInfo.setShares(newShares);

                        diskUpdated = true;
                    }

                    long currentLimitIops = sioai.getLimit() !=  null ? sioai.getLimit() : Long.MIN_VALUE;
                    long newLimitIops = getNewLimitIopsBasedOnVolumeSize(volumeVO, limitIopsPerGB);

                    limitIopsTotal += newLimitIops;

                    if (currentLimitIops != newLimitIops) {
                        sioai.setLimit(newLimitIops);

                        diskUpdated = true;
                    }

                    if (diskUpdated) {
                        VirtualDeviceConfigSpec vdcs = new VirtualDeviceConfigSpec();

                        vdcs.setDevice(disk);
                        vdcs.setOperation(VirtualDeviceConfigSpecOperation.EDIT);

                        VirtualMachineConfigSpec vmcs = new VirtualMachineConfigSpec();

                        vmcs.getDeviceChange().add(vdcs);

                        try {
                            ManagedObjectReference task = VMwareUtil.reconfigureVm(connection, morVm, vmcs);

                            tasks.add(task);

                            LOGGER.info(getInfoMsg(volumeVO, newShares, newLimitIops));
                        } catch (Exception ex) {
                            throw new Exception("Error: " + ex.getMessage());
                        }
                    }
                }
            }
        }

        return new ResultWrapper(limitIopsTotal, tasks);
    }

};