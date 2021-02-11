//,temp,VirtualMachineMO.java,1347,1376,temp,VirtualMachineMO.java,1315,1345
//,3
public class xxx {
    public void attachDisk(Pair<String, ManagedObjectReference>[] vmdkDatastorePathChain, int controllerKey) throws Exception {

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - attachDisk(). target MOR: " + _mor.getValue() + ", vmdkDatastorePath: " + new Gson().toJson(vmdkDatastorePathChain));

        synchronized (_mor.getValue().intern()) {
            VirtualDevice newDisk = VmwareHelper.prepareDiskDevice(this, controllerKey, vmdkDatastorePathChain, -1, 1);
            VirtualMachineConfigSpec reConfigSpec = new VirtualMachineConfigSpec();
            VirtualDeviceConfigSpec deviceConfigSpec = new VirtualDeviceConfigSpec();

            deviceConfigSpec.setDevice(newDisk);
            deviceConfigSpec.setOperation(VirtualDeviceConfigSpecOperation.ADD);

            reConfigSpec.getDeviceChange().add(deviceConfigSpec);

            ManagedObjectReference morTask = _context.getService().reconfigVMTask(_mor, reConfigSpec);
            boolean result = _context.getVimClient().waitForTask(morTask);

            if (!result) {
                if (s_logger.isTraceEnabled())
                    s_logger.trace("vCenter API trace - attachDisk() done(failed)");
                throw new Exception("Failed to attach disk due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }

            _context.waitForTaskProgressDone(morTask);
        }

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - attachDisk() done(successfully)");
    }

};