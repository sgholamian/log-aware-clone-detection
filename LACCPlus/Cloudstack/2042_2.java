//,temp,HypervDirectConnectResource.java,1616,1661,temp,HypervDirectConnectResource.java,1574,1614
//,3
public class xxx {
    protected Answer execute(final CreateIpAliasCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing createIpAlias command: " + s_gson.toJson(cmd));
        }
        cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
        final List<IpAliasTO> ipAliasTOs = cmd.getIpAliasList();
        final StringBuilder args = new StringBuilder();
        for (final IpAliasTO ipaliasto : ipAliasTOs) {
            args.append(ipaliasto.getAlias_count());
            args.append(":");
            args.append(ipaliasto.getRouterip());
            args.append(":");
            args.append(ipaliasto.getNetmask());
            args.append("-");
        }
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Run command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + ", /root/createIpAlias " + args);
        }

        try {
            final String controlIp = getRouterSshControlIp(cmd);
            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, "/root/createIpAlias.sh " + args);

            if (!result.first()) {
                s_logger.error("CreateIpAlias command on domr " + controlIp + " failed, message: " + result.second());

                return new Answer(cmd, false, "createipAlias failed due to " + result.second());
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("createIpAlias command on domain router " + controlIp + " completed");
            }

        } catch (final Throwable e) {
            final String msg = "createIpAlias failed due to " + e;
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd);
    }

};