//,temp,VmwareResource.java,1185,1263,temp,VmwareResource.java,1101,1183
//,3
public class xxx {
    private ReplugNicAnswer execute(ReplugNicCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource ReplugNicCommand " + _gson.toJson(cmd));
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
                String msg = "Router " + vmName + " no longer exists to execute ReplugNic command";
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
                nicDeviceType = VirtualEthernetCardType.valueOf((String) details.get("nicAdapter"));
            }

            NicTO nicTo = cmd.getNic();

            VirtualDevice nic = findVirtualNicDevice(vmMo, nicTo.getMac());
            if (nic == null) {
                return new ReplugNicAnswer(cmd, false, "Nic to replug not found");
            }

            Pair<ManagedObjectReference, String> networkInfo = prepareNetworkFromNicInfo(vmMo.getRunningHost(), nicTo, false, cmd.getVMType());
            String dvSwitchUuid = null;
            if (VmwareHelper.isDvPortGroup(networkInfo.first())) {
                ManagedObjectReference dcMor = hyperHost.getHyperHostDatacenter();
                DatacenterMO dataCenterMo = new DatacenterMO(context, dcMor);
                ManagedObjectReference dvsMor = dataCenterMo.getDvSwitchMor(networkInfo.first());
                dvSwitchUuid = dataCenterMo.getDvSwitchUuid(dvsMor);
                s_logger.info("Preparing NIC device on dvSwitch : " + dvSwitchUuid);
                VmwareHelper.updateDvNicDevice(nic, networkInfo.first(), dvSwitchUuid);
            } else {
                s_logger.info("Preparing NIC device on network " + networkInfo.second());

                VmwareHelper.updateNicDevice(nic, networkInfo.first(), networkInfo.second());
            }

            VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
            //VirtualDeviceConfigSpec[] deviceConfigSpecArray = new VirtualDeviceConfigSpec[1];
            VirtualDeviceConfigSpec deviceConfigSpec = new VirtualDeviceConfigSpec();
            deviceConfigSpec.setDevice(nic);
            deviceConfigSpec.setOperation(VirtualDeviceConfigSpecOperation.EDIT);

            vmConfigSpec.getDeviceChange().add(deviceConfigSpec);
            if (!vmMo.configureVm(vmConfigSpec)) {
                throw new Exception("Failed to configure devices when running ReplugNicCommand");
            }

            return new ReplugNicAnswer(cmd, true, "success");
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
            return new ReplugNicAnswer(cmd, false, "Unable to execute ReplugNicCommand due to " + e.toString());
        }
    }

};