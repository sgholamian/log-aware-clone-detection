//,temp,HypervDirectConnectResource.java,2081,2107,temp,HypervDirectConnectResource.java,1037,1072
//,3
public class xxx {
    protected Answer execute(final SetMonitorServiceCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource SetMonitorServiceCommand: " + s_gson.toJson(cmd));
        }

        final String controlIp = getRouterSshControlIp(cmd);
        final String config = cmd.getConfiguration();
        final String args = String.format(" %s %s", "-c", config);

        final String command = String.format("%s%s %s", "/opt/cloud/bin/", VRScripts.MONITOR_SERVICE, args);

        try {
            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                final String msg=  "monitor_service.sh failed on domain router " + controlIp + " failed " + result.second();
                s_logger.error(msg);
                return new Answer(cmd, false, msg);
            }

            return new Answer(cmd);

        } catch (final Throwable e) {
            s_logger.error("Unexpected exception: " + e.toString(), e);
            return new Answer(cmd, false, "SetMonitorServiceCommand failed due to " + e);
        }
    }

};