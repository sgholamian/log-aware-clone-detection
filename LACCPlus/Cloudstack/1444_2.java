//,temp,CitrixResourceBase.java,5017,5053,temp,CitrixResourceBase.java,4413,4446
//,3
public class xxx {
    public void rebootVM(final Connection conn, final VM vm, final String vmName) throws Exception {
        Task task = null;
        try {
            task = vm.cleanRebootAsync(conn);
            try {
                // poll every 1 seconds , timeout after 10 minutes
                waitForTask(conn, task, 1000, 10 * 60 * 1000);
                checkForSuccess(conn, task);
            } catch (final Types.HandleInvalid e) {
                if (vm.getPowerState(conn) == VmPowerState.RUNNING) {
                    task = null;
                    return;
                }
                throw new CloudRuntimeException("Reboot VM catch HandleInvalid and VM is not in RUNNING state");
            }
        } catch (final XenAPIException e) {
            s_logger.debug("Unable to Clean Reboot VM(" + vmName + ") on host(" + _host.getUuid() + ") due to " + e.toString() + ", try hard reboot");
            try {
                vm.hardReboot(conn);
            } catch (final Exception e1) {
                final String msg = "Unable to hard Reboot VM(" + vmName + ") on host(" + _host.getUuid() + ") due to " + e.toString();
                s_logger.warn(msg, e1);
                throw new CloudRuntimeException(msg);
            }
        } finally {
            if (task != null) {
                try {
                    task.destroy(conn);
                } catch (final Exception e1) {
                    s_logger.debug("unable to destroy task(" + task.toString() + ") on host(" + _host.getUuid() + ") due to " + e1.toString());
                }
            }
        }
    }

};