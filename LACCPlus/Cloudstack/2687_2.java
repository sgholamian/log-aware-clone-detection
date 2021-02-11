//,temp,VmwareResource.java,5200,5240,temp,VmwareResource.java,3623,3680
//,3
public class xxx {
    protected Answer execute(RebootCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource RebootCommand: " + _gson.toJson(cmd));
        }

        boolean toolsInstallerMounted = false;
        VirtualMachineMO vmMo = null;
        VmwareContext context = getServiceContext();
        VmwareHypervisorHost hyperHost = getHyperHost(context);
        try {
            vmMo = hyperHost.findVmOnHyperHost(cmd.getVmName());
            if (vmMo != null) {
                if (vmMo.isToolsInstallerMounted()) {
                    toolsInstallerMounted = true;
                    s_logger.trace("Detected mounted vmware tools installer for :[" + cmd.getVmName() + "]");
                }
                try {
                    vmMo.rebootGuest();
                    return new RebootAnswer(cmd, "reboot succeeded", true);
                } catch (ToolsUnavailableFaultMsg e) {
                    s_logger.warn("VMware tools is not installed at guest OS, we will perform hard reset for reboot");
                } catch (Exception e) {
                    s_logger.warn("We are not able to perform gracefull guest reboot due to " + VmwareHelper.getExceptionMessage(e));
                }

                // continue to try with hard-reset
                if (vmMo.reset()) {
                    return new RebootAnswer(cmd, "reboot succeeded", true);
                }

                String msg = "Reboot failed in vSphere. vm: " + cmd.getVmName();
                s_logger.warn(msg);
                return new RebootAnswer(cmd, msg, false);
            } else {
                String msg = "Unable to find the VM in vSphere to reboot. vm: " + cmd.getVmName();
                s_logger.warn(msg);
                return new RebootAnswer(cmd, msg, false);
            }
        } catch (Exception e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            String msg = "RebootCommand failed due to " + VmwareHelper.getExceptionMessage(e);
            s_logger.error(msg);
            return new RebootAnswer(cmd, msg, false);
        } finally {
            if (toolsInstallerMounted) {
                try {
                    vmMo.mountToolsInstaller();
                    s_logger.debug("Successfully re-mounted vmware tools installer for :[" + cmd.getVmName() + "]");
                } catch (Exception e) {
                    s_logger.warn("Unabled to re-mount vmware tools installer for :[" + cmd.getVmName() + "]");
                }
            }
        }
    }

};