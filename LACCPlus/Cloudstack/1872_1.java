//,temp,VirtualMachineMO.java,2328,2352,temp,VirtualMachineMO.java,2180,2203
//,3
public class xxx {
    public void ensureLsiLogicDeviceControllers(int count, int availableBusNum) throws Exception {
        int scsiControllerKey = getLsiLogicDeviceControllerKeyNoException();
        if (scsiControllerKey < 0) {
            VirtualMachineConfigSpec vmConfig = new VirtualMachineConfigSpec();

            int busNum = availableBusNum;
            while (busNum < count) {
                VirtualLsiLogicController scsiController = new VirtualLsiLogicController();
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
                throw new Exception("Unable to add Lsi Logic controllers to the VM " + getName());
            } else {
                s_logger.info("Successfully added " + count + " LsiLogic Parallel SCSI controllers.");
            }
        }
    }

};