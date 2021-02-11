//,temp,MockVmMgr.java,116,133,temp,MockVmMgr.java,87,101
//,3
public class xxx {
    @Override
    public String stopVM(String vmName, boolean force) {
        if (s_logger.isInfoEnabled())
            s_logger.info("Stop VM. name: " + vmName);

        synchronized (this) {
            MockVm vm = vms.get(vmName);
            if (vm != null) {
                vm.setState(State.Stopped);
                freeVncPort(vm.getVncPort());
            }
        }

        return null;
    }

};