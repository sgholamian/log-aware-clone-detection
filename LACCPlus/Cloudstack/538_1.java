//,temp,VmwareStorageManagerImpl.java,1390,1463,temp,VmwareStorageManagerImpl.java,1174,1241
//,3
public class xxx {
    @Override
    public RevertToVMSnapshotAnswer execute(VmwareHostService hostService, RevertToVMSnapshotCommand cmd) {
        String snapshotName = cmd.getTarget().getSnapshotName();
        String vmName = cmd.getVmName();
        Boolean snapshotMemory = cmd.getTarget().getType() == VMSnapshot.Type.DiskAndMemory;
        List<VolumeObjectTO> listVolumeTo = cmd.getVolumeTOs();
        VirtualMachine.PowerState vmState = VirtualMachine.PowerState.PowerOn;
        VirtualMachineMO vmMo = null;
        VmwareContext context = hostService.getServiceContext(cmd);

        try {
            VmwareHypervisorHost hyperHost = hostService.getHyperHost(context, cmd);

            // wait if there are already VM revert task running
            ManagedObjectReference taskmgr = context.getServiceContent().getTaskManager();
            List<ManagedObjectReference> tasks = context.getVimClient().getDynamicProperty(taskmgr, "recentTask");

            for (ManagedObjectReference taskMor : tasks) {
                TaskInfo info = (TaskInfo)(context.getVimClient().getDynamicProperty(taskMor, "info"));

                if (info.getEntityName().equals(cmd.getVmName()) && StringUtils.isNotBlank(info.getName()) && info.getName().equalsIgnoreCase("RevertToSnapshot_Task")) {
                    s_logger.debug("There is already a VM snapshot task running, wait for it");
                    context.getVimClient().waitForTask(taskMor);
                }
            }

            HostMO hostMo = (HostMO)hyperHost;
            vmMo = hyperHost.findVmOnHyperHost(vmName);

            if (vmMo == null) {
                vmMo = hyperHost.findVmOnPeerHyperHost(vmName);
            }

            if (vmMo == null) {
                String msg = "Unable to find VM for RevertToVMSnapshotCommand";
                s_logger.debug(msg);

                return new RevertToVMSnapshotAnswer(cmd, false, msg);
            } else {
                if (cmd.isReloadVm()) {
                    vmMo.reload();
                }

                boolean result = false;

                if (snapshotName != null) {
                    ManagedObjectReference morSnapshot = vmMo.getSnapshotMor(snapshotName);

                    result = hostMo.revertToSnapshot(morSnapshot);
                } else {
                    return new RevertToVMSnapshotAnswer(cmd, false, "Unable to find the snapshot by name " + snapshotName);
                }

                if (result) {
                    Map<String, String> mapNewDisk = getNewDiskMap(vmMo);

                    setVolumeToPathAndSize(listVolumeTo, mapNewDisk, context, hyperHost, cmd.getVmName());

                    if (!snapshotMemory) {
                        vmState = VirtualMachine.PowerState.PowerOff;
                    }

                    return new RevertToVMSnapshotAnswer(cmd, listVolumeTo, vmState);
                } else {
                    return new RevertToVMSnapshotAnswer(cmd, false, "Error while reverting to snapshot due to execute in ESXi");
                }
            }
        } catch (Exception e) {
            String msg = "revert vm " + vmName + " to snapshot " + snapshotName + " failed due to " + e.getMessage();
            s_logger.error(msg);

            return new RevertToVMSnapshotAnswer(cmd, false, msg);
        }
    }

};