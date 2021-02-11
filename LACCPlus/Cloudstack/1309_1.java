//,temp,HypervDirectConnectResource.java,1217,1267,temp,HypervDirectConnectResource.java,801,844
//,3
public class xxx {
    protected Answer execute(final DeleteIpAliasCommand cmd) {
        cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
        final List<IpAliasTO> revokedIpAliasTOs = cmd.getDeleteIpAliasTos();
        final List<IpAliasTO> activeIpAliasTOs = cmd.getCreateIpAliasTos();
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing deleteIpAlias command: " + s_gson.toJson(cmd));
        }
        final StringBuilder args = new StringBuilder();
        for (final IpAliasTO ipAliasTO : revokedIpAliasTOs) {
            args.append(ipAliasTO.getAlias_count());
            args.append(":");
            args.append(ipAliasTO.getRouterip());
            args.append(":");
            args.append(ipAliasTO.getNetmask());
            args.append("-");
        }
        args.append("- ");
        for (final IpAliasTO ipAliasTO : activeIpAliasTOs) {
            args.append(ipAliasTO.getAlias_count());
            args.append(":");
            args.append(ipAliasTO.getRouterip());
            args.append(":");
            args.append(ipAliasTO.getNetmask());
            args.append("-");
        }
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Run command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + ", /root/deleteIpAlias " + args);
        }

        try {
            final String controlIp = getRouterSshControlIp(cmd);
            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, "/root/deleteIpAlias.sh " + args);

            if (!result.first()) {
                s_logger.error("deleteIpAlias command on domr " + controlIp + " failed, message: " + result.second());

                return new Answer(cmd, false, "deleteIpAlias failed due to " + result.second());
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("deleteIpAlias command on domain router " + controlIp + " completed");
            }

        } catch (final Throwable e) {
            final String msg = "deleteIpAlias failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd);
    }

};