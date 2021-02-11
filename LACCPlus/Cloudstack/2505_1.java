//,temp,HypervDirectConnectResource.java,1117,1146,temp,HypervDirectConnectResource.java,931,967
//,3
public class xxx {
    protected Answer execute(final CheckRouterCommand cmd) {
        final String command = String.format("%s%s", "/opt/cloud/bin/", VRScripts.RVR_CHECK);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Executing resource CheckRouterCommand: " + s_gson.toJson(cmd));
            s_logger.debug("Run command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + command);
        }

        Pair<Boolean, String> result;
        try {

            final String controlIp = getRouterSshControlIp(cmd);
            result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                s_logger.error("check router command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + " failed, message: " + result.second());

                return new CheckRouterAnswer(cmd, "CheckRouter failed due to " + result.second());
            }

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("check router command on domain router " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + " completed");
            }
        } catch (final Throwable e) {
            final String msg = "CheckRouterCommand failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new CheckRouterAnswer(cmd, msg);
        }
        return new CheckRouterAnswer(cmd, result.second(), true);
    }

};