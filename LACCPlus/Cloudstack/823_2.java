//,temp,VmwareResource.java,4818,4838,temp,VmwareResource.java,3682,3713
//,3
public class xxx {
    protected Answer execute(CheckVirtualMachineCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CheckVirtualMachineCommand: " + _gson.toJson(cmd));
        }

        final String vmName = cmd.getVmName();
        PowerState powerState = PowerState.PowerUnknown;
        Integer vncPort = null;

        VmwareContext context = getServiceContext();
        VmwareHypervisorHost hyperHost = getHyperHost(context);

        try {
            VirtualMachineMO vmMo = hyperHost.findVmOnHyperHost(vmName);
            if (vmMo != null) {
                powerState = getVmPowerState(vmMo);
                return new CheckVirtualMachineAnswer(cmd, powerState, vncPort);
            } else {
                s_logger.warn("Can not find vm " + vmName + " to execute CheckVirtualMachineCommand");
                return new CheckVirtualMachineAnswer(cmd, powerState, vncPort);
            }

        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }
            s_logger.error("Unexpected exception: " + VmwareHelper.getExceptionMessage(e), e);

            return new CheckVirtualMachineAnswer(cmd, powerState, vncPort);
        }
    }

};