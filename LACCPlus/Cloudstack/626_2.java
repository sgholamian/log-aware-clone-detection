//,temp,CitrixResourceBase.java,5017,5053,temp,CitrixResourceBase.java,4954,5010
//,3
public class xxx {
    public void shutdownVM(final Connection conn, final VM vm, final String vmName, final boolean forcedStop) throws XmlRpcException {
        Task task = null;
        try {
            if (forcedStop) {
                task = vm.hardShutdownAsync(conn);
            } else {
                task = vm.cleanShutdownAsync(conn);
            }

            try {
                // poll every 1 seconds , timeout after 10 minutes
                waitForTask(conn, task, 1000, 10 * 60 * 1000);
                checkForSuccess(conn, task);
            } catch (final TimeoutException e) {
                if (vm.getPowerState(conn) == VmPowerState.HALTED) {
                    task = null;
                    return;
                }
                throw new CloudRuntimeException("Shutdown VM catch HandleInvalid and VM is not in HALTED state");
            }
        } catch (final XenAPIException e) {
            s_logger.debug("Unable to shutdown VM(" + vmName + ") with force=" + forcedStop + " on host(" + _host.getUuid() + ") due to " + e.toString());
            try {
                VmPowerState state = vm.getPowerState(conn);
                if (state == VmPowerState.RUNNING) {
                    try {
                        vm.hardShutdown(conn);
                    } catch (final Exception e1) {
                        s_logger.debug("Unable to hardShutdown VM(" + vmName + ") on host(" + _host.getUuid() + ") due to " + e.toString());
                        state = vm.getPowerState(conn);
                        if (state == VmPowerState.RUNNING) {
                            forceShutdownVM(conn, vm);
                        }
                        return;
                    }
                } else if (state == VmPowerState.HALTED) {
                    return;
                } else {
                    final String msg = "After cleanShutdown the VM status is " + state.toString() + ", that is not expected";
                    s_logger.warn(msg);
                    throw new CloudRuntimeException(msg);
                }
            } catch (final Exception e1) {
                final String msg = "Unable to hardShutdown VM(" + vmName + ") on host(" + _host.getUuid() + ") due to " + e.toString();
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