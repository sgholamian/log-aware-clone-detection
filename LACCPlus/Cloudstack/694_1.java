//,temp,VirtualMachineMO.java,3350,3376,temp,VirtualMachineMO.java,2390,2414
//,2
public class xxx {
    public void ensureBusLogicDeviceControllers(int count, int availableBusNum) throws Exception {
        int scsiControllerKey = getBusLogicDeviceControllerKeyNoException();
        if (scsiControllerKey < 0) {
            VirtualMachineConfigSpec vmConfig = new VirtualMachineConfigSpec();

            int busNum = availableBusNum;
            while (busNum < count) {
                VirtualBusLogicController scsiController = new VirtualBusLogicController();

                scsiController.setSharedBus(VirtualSCSISharing.NO_SHARING);
                scsiController.setBusNumber(busNum);
                scsiController.setKey(busNum - VmwareHelper.MAX_SCSI_CONTROLLER_COUNT);
                VirtualDeviceConfigSpec scsiControllerSpec = new VirtualDeviceConfigSpec();
                scsiControllerSpec.setDevice(scsiController);
                scsiControllerSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);

                vmConfig.getDeviceChange().add(scsiControllerSpec);
                busNum++;
            }

            if (configureVm(vmConfig)) {
                throw new Exception("Unable to add Scsi BusLogic controllers to the VM " + getName());
            } else {
                s_logger.info("Successfully added " + count + " SCSI BusLogic controllers.");
            }
        }
    }

};