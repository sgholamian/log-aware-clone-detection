//,temp,HypervDirectConnectResource.java,1217,1267,temp,HypervDirectConnectResource.java,801,844
//,3
public class xxx {
    protected Answer execute(final RemoteAccessVpnCfgCommand cmd) {
        final String controlIp = getRouterSshControlIp(cmd);
        final StringBuffer argsBuf = new StringBuffer();
        if (cmd.isCreate()) {
            argsBuf.append(" -r ").append(cmd.getIpRange()).append(" -p ").append(cmd.getPresharedKey()).append(" -s ").append(cmd.getVpnServerIp()).append(" -l ").append(cmd.getLocalIp())
            .append(" -c ");

        } else {
            argsBuf.append(" -d ").append(" -s ").append(cmd.getVpnServerIp());
        }
        argsBuf.append(" -C ").append(cmd.getLocalCidr());
        argsBuf.append(" -i ").append(cmd.getPublicInterface());

        try {
            final String command = String.format("%s%s %s", "/opt/cloud/bin/", VRScripts.VPN_L2TP, argsBuf.toString());

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Executing " + command);
            }

            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                s_logger.error("RemoteAccessVpnCfg command on domR failed, message: " + result.second());

                return new Answer(cmd, false, "RemoteAccessVpnCfg command failed due to " + result.second());
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("RemoteAccessVpnCfg command on domain router " + argsBuf.toString() + " completed");
            }

        } catch (final Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn(e.getMessage());
            }

            final String msg = "RemoteAccessVpnCfg command failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd);
    }

};