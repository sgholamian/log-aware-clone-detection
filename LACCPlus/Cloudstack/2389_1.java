//,temp,MockVmMgr.java,116,133,temp,MockVmMgr.java,87,101
//,3
public class xxx {
    @Override
    public boolean migrate(String vmName, String params) {
        if (s_logger.isInfoEnabled())
            s_logger.info("Migrate VM. name: " + vmName);

        synchronized (this) {
            MockVm vm = vms.get(vmName);
            if (vm != null) {
                vm.setState(State.Stopped);
                freeVncPort(vm.getVncPort());

                vms.remove(vmName);
                return true;
            }
        }

        return false;
    }

};