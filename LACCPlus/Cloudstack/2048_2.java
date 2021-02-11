//,temp,VmwareResource.java,1185,1263,temp,VmwareResource.java,1101,1183
//,3
public class xxx {
    private PlugNicAnswer execute(PlugNicCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource PlugNicCommand " + _gson.toJson(cmd));
        }

        getServiceContext().getStockObject(VmwareManager.CONTEXT_STOCK_NAME);
        VmwareContext context = getServiceContext();
        try {
            VmwareHypervisorHost hyperHost = getHyperHost(context);

            String vmName = cmd.getVmName();
            VirtualMachineMO vmMo = hyperHost.findVmOnHyperHost(vmName);

            if (vmMo == null) {
                if (hyperHost instanceof HostMO) {
                    ClusterMO clusterMo = new ClusterMO(hyperHost.getContext(), ((HostMO)hyperHost).getParentMor());
                    vmMo = clusterMo.findVmOnHyperHost(vmName);
                }
            }

            if (vmMo == null) {
                String msg = "Router " + vmName + " no longer exists to execute PlugNic command";
                s_logger.error(msg);
                throw new Exception(msg);
            }

            /*
            if(!isVMWareToolsInstalled(vmMo)){
                String errMsg = "vmware tools is not installed or not running, cannot add nic to vm " + vmName;
                s_logger.debug(errMsg);
                return new PlugNicAnswer(cmd, false, "Unable to execute PlugNicCommand due to " + errMsg);
            }
             */
            // Fallback to E1000 if no specific nicAdapter is passed
            VirtualEthernetCardType nicDeviceType = VirtualEthernetCardType.E1000;
            Map<String, String> details = cmd.getDetails();
            if (details != null) {
                nicDeviceType = VirtualEthernetCardType.valueOf((String)details.get("nicAdapter"));
            }

            // find a usable device number in VMware environment
            VirtualDevice[] nicDevices = vmMo.getNicDevices();
            int deviceNumber = -1;
            for (VirtualDevice device : nicDevices) {
                if (device.getUnitNumber() > deviceNumber)
                    deviceNumber = device.getUnitNumber();
            }
            deviceNumber++;

            NicTO nicTo = cmd.getNic();
            VirtualDevice nic;
            Pair<ManagedObjectReference, String> networkInfo = prepareNetworkFromNicInfo(vmMo.getRunningHost(), nicTo, false, cmd.getVMType());
            String dvSwitchUuid = null;
            if (VmwareHelper.isDvPortGroup(networkInfo.first())) {
                ManagedObjectReference dcMor = hyperHost.getHyperHostDatacenter();
                DatacenterMO dataCenterMo = new DatacenterMO(context, dcMor);
                ManagedObjectReference dvsMor = dataCenterMo.getDvSwitchMor(networkInfo.first());
                dvSwitchUuid = dataCenterMo.getDvSwitchUuid(dvsMor);
                s_logger.info("Preparing NIC device on dvSwitch : " + dvSwitchUuid);
                nic = VmwareHelper.prepareDvNicDevice(vmMo, networkInfo.first(), nicDeviceType, networkInfo.second(), dvSwitchUuid,
                        nicTo.getMac(), deviceNumber + 1, true, true);
            } else {
                s_logger.info("Preparing NIC device on network " + networkInfo.second());
                nic = VmwareHelper.prepareNicDevice(vmMo, networkInfo.first(), nicDeviceType, networkInfo.second(),
                        nicTo.getMac(), deviceNumber + 1, true, true);
            }

            VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
            VirtualDeviceConfigSpec deviceConfigSpec = new VirtualDeviceConfigSpec();
            deviceConfigSpec.setDevice(nic);
            deviceConfigSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);

            vmConfigSpec.getDeviceChange().add(deviceConfigSpec);
            if (!vmMo.configureVm(vmConfigSpec)) {
                throw new Exception("Failed to configure devices when running PlugNicCommand");
            }

            return new PlugNicAnswer(cmd, true, "success");
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
            return new PlugNicAnswer(cmd, false, "Unable to execute PlugNicCommand due to " + e.toString());
        }
    }

};