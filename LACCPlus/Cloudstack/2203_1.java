//,temp,MockVmMgr.java,103,114,temp,MockVmMgr.java,87,101
//,3
public class xxx {
    @Override
    public String rebootVM(String vmName) {
        if (s_logger.isInfoEnabled())
            s_logger.info("Reboot VM. name: " + vmName);

        synchronized (this) {
            MockVm vm = vms.get(vmName);
            if (vm != null)
                vm.setState(State.Running);
        }
        return null;
    }

};