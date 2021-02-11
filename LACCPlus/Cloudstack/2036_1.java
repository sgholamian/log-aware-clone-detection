//,temp,SiocManagerImpl.java,277,335,temp,SiocManagerImpl.java,177,263
//,3
public class xxx {
    private ResultWrapper updateSiocInfoForWorkerVM(VMwareUtil.VMwareConnection connection, ManagedObjectReference morVm, String datastoreName,
                                                    int limitIopsPerGB) throws Exception {
        int limitIopsTotal = 0;
        List<ManagedObjectReference> tasks = new ArrayList<>();

        VirtualMachineConfigInfo vmci = (VirtualMachineConfigInfo)VMwareUtil.getEntityProps(connection, morVm,
                new String[] { "config" }).get("config");
        List<VirtualDevice> devices = vmci.getHardware().getDevice();

        for (VirtualDevice device : devices) {
            if (device instanceof VirtualDisk) {
                VirtualDisk disk = (VirtualDisk)device;

                if (disk.getBacking() instanceof VirtualDeviceFileBackingInfo) {
                    VirtualDeviceFileBackingInfo backingInfo = (VirtualDeviceFileBackingInfo)disk.getBacking();

                    if (backingInfo.getFileName().contains(datastoreName)) {
                        boolean diskUpdated = false;

                        StorageIOAllocationInfo sioai = disk.getStorageIOAllocation();

                        long currentLimitIops = sioai.getLimit() !=  null ? sioai.getLimit() : Long.MIN_VALUE;
                        long newLimitIops = getNewLimitIopsBasedOnVolumeSize(disk.getCapacityInBytes(), limitIopsPerGB);

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

                                LOGGER.info(getInfoMsgForWorkerVm(newLimitIops));
                            } catch (Exception ex) {
                                throw new Exception("Error: " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        }

        return new ResultWrapper(limitIopsTotal, tasks);
    }

};