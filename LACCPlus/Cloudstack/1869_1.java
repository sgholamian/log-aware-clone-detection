//,temp,VirtualMachineMO.java,1024,1038,temp,VirtualMachineMO.java,412,424
//,3
public class xxx {
    public boolean setVncConfigInfo(boolean enableVnc, String vncPassword, int vncPort, String keyboard) throws Exception {
        VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
        OptionValue[] vncOptions = VmwareHelper.composeVncOptions(null, enableVnc, vncPassword, vncPort, keyboard);
        vmConfigSpec.getExtraConfig().addAll(Arrays.asList(vncOptions));
        ManagedObjectReference morTask = _context.getService().reconfigVMTask(_mor, vmConfigSpec);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware reconfigVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }
        return false;
    }

};