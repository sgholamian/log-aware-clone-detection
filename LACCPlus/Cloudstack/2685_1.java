//,temp,HypervDirectConnectResource.java,1992,2024,temp,HypervDirectConnectResource.java,1117,1146
//,3
public class xxx {
    protected Answer execute(final GetDomRVersionCmd cmd) {
        final String command = String.format("%s%s", "/opt/cloud/bin/", VRScripts.VERSION);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Executing resource GetDomRVersionCmd: " + s_gson.toJson(cmd));
            s_logger.debug("Run command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + command);
        }

        Pair<Boolean, String> result;
        try {
            final String controlIp = getRouterSshControlIp(cmd);
            result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                s_logger.error("GetDomRVersionCmd on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + " failed, message: " + result.second());

                return new GetDomRVersionAnswer(cmd, "GetDomRVersionCmd failed due to " + result.second());
            }

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("GetDomRVersionCmd on domain router " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + " completed");
            }
        } catch (final Throwable e) {
            final String msg = "GetDomRVersionCmd failed due to " + e;
            s_logger.error(msg, e);
            return new GetDomRVersionAnswer(cmd, msg);
        }
        final String[] lines = result.second().split("&");
        if (lines.length != 2) {
            return new GetDomRVersionAnswer(cmd, result.second());
        }
        return new GetDomRVersionAnswer(cmd, result.second(), lines[0], lines[1]);
    }

};