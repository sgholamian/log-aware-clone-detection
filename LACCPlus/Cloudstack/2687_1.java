//,temp,VmwareResource.java,5200,5240,temp,VmwareResource.java,3623,3680
//,3
public class xxx {
    protected Answer execute(UnregisterVMCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource UnregisterVMCommand: " + _gson.toJson(cmd));
        }

        VmwareContext context = getServiceContext();
        VmwareHypervisorHost hyperHost = getHyperHost(context);
        try {
            DatacenterMO dataCenterMo = new DatacenterMO(getServiceContext(), hyperHost.getHyperHostDatacenter());
            VirtualMachineMO vmMo = hyperHost.findVmOnHyperHost(cmd.getVmName());
            if (vmMo != null) {
                try {
                    VirtualMachineFileLayoutEx vmFileLayout = vmMo.getFileLayout();
                    context.getService().unregisterVM(vmMo.getMor());
                    if (cmd.getCleanupVmFiles()) {
                        deleteUnregisteredVmFiles(vmFileLayout, dataCenterMo, false, null);
                    }
                    return new Answer(cmd, true, "unregister succeeded");
                } catch (Exception e) {
                    s_logger.warn("We are not able to unregister VM " + VmwareHelper.getExceptionMessage(e));
                }

                String msg = "Expunge failed in vSphere. vm: " + cmd.getVmName();
                s_logger.warn(msg);
                return new Answer(cmd, false, msg);
            } else {
                String msg = "Unable to find the VM in vSphere to unregister, assume it is already removed. VM: " + cmd.getVmName();
                s_logger.warn(msg);
                return new Answer(cmd, true, msg);
            }
        } catch (Exception e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            String msg = "UnregisterVMCommand failed due to " + VmwareHelper.getExceptionMessage(e);
            s_logger.error(msg);
            return new Answer(cmd, false, msg);
        }
    }

};