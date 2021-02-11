//,temp,HypervDirectConnectResource.java,1574,1614,temp,HypervDirectConnectResource.java,931,967
//,3
public class xxx {
    protected CheckS2SVpnConnectionsAnswer execute(final CheckS2SVpnConnectionsCommand cmd) {
        final StringBuilder cmdline = new StringBuilder();
        cmdline.append("/opt/cloud/bin/");
        cmdline.append(VRScripts.S2SVPN_CHECK);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Executing resource CheckS2SVpnConnectionsCommand: " + s_gson.toJson(cmd));
            s_logger.debug("Run command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + cmdline.toString());
        }

        Pair<Boolean, String> result;
        try {
            final String controlIp = getRouterSshControlIp(cmd);
            for (final String ip : cmd.getVpnIps()) {
                cmdline.append(" ");
                cmdline.append(ip);
            }

            result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, cmdline.toString());

            if (!result.first()) {
                s_logger.error("check site-to-site vpn connections command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + " failed, message: " +
                        result.second());

                return new CheckS2SVpnConnectionsAnswer(cmd, false, result.second());
            }

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("check site-to-site vpn connections command on domain router " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + " completed");
            }
        } catch (final Throwable e) {
            final String msg = "CheckS2SVpnConnectionsCommand failed due to " + e;
            s_logger.error(msg, e);
            return new CheckS2SVpnConnectionsAnswer(cmd, false, "CheckS2SVpnConneciontsCommand failed");
        }
        return new CheckS2SVpnConnectionsAnswer(cmd, true, result.second());
    }

};