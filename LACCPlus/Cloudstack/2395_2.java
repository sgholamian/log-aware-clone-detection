//,temp,DatastoreMO.java,157,172,temp,VirtualMachineMO.java,450,465
//,3
public class xxx {
    public boolean changeDatastore(ManagedObjectReference morDataStore) throws Exception {
        VirtualMachineRelocateSpec relocateSpec = new VirtualMachineRelocateSpec();
        relocateSpec.setDatastore(morDataStore);

        ManagedObjectReference morTask = _context.getService().relocateVMTask(_mor, relocateSpec, null);

        boolean result = _context.getVimClient().waitForTask(morTask);
        if (result) {
            _context.waitForTaskProgressDone(morTask);
            return true;
        } else {
            s_logger.error("VMware change datastore relocateVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
        }

        return false;
    }

};