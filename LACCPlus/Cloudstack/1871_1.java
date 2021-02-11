//,temp,VirtualMachineMO.java,1451,1481,temp,VirtualMachineMO.java,1315,1345
//,3
public class xxx {
    public void detachAllDisks() throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - detachAllDisk(). target MOR: " + _mor.getValue());

        VirtualDisk[] disks = getAllDiskDevice();
        if (disks.length > 0) {
            VirtualMachineConfigSpec reConfigSpec = new VirtualMachineConfigSpec();
            VirtualDeviceConfigSpec[] deviceConfigSpecArray = new VirtualDeviceConfigSpec[disks.length];

            for (int i = 0; i < disks.length; i++) {
                deviceConfigSpecArray[i] = new VirtualDeviceConfigSpec();
                deviceConfigSpecArray[i].setDevice(disks[i]);
                deviceConfigSpecArray[i].setOperation(VirtualDeviceConfigSpecOperation.REMOVE);
            }
            reConfigSpec.getDeviceChange().addAll(Arrays.asList(deviceConfigSpecArray));

            ManagedObjectReference morTask = _context.getService().reconfigVMTask(_mor, reConfigSpec);
            boolean result = _context.getVimClient().waitForTask(morTask);

            if (!result) {
                if (s_logger.isTraceEnabled())
                    s_logger.trace("vCenter API trace - detachAllDisk() done(failed)");
                throw new Exception("Failed to detach disk due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }

            _context.waitForTaskProgressDone(morTask);
        }

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - detachAllDisk() done(successfully)");
    }

};