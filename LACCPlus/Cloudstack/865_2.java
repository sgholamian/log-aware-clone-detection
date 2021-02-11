//,temp,VirtualMachineMO.java,537,554,temp,VirtualMachineMO.java,467,482
//,3
public class xxx {
    public boolean relocate(ManagedObjectReference morTargetHost) throws Exception {
        VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
        relocateSpec.setHost(morTargetHost);

        ManagedObjectReference morTask = _context.getService().relocateVMTask(_mor, relocateSpec, null);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware relocateVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        return false;
    }

};