//,temp,HypervDirectConnectResource.java,1616,1661,temp,HypervDirectConnectResource.java,1574,1614
//,3
public class xxx {
    protected Answer execute(final DnsMasqConfigCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing dnsmasqConfig command: " + s_gson.toJson(cmd));
        }
        final String routerIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
        final String controlIp = getRouterSshControlIp(cmd);

        assert controlIp != null;

        final List<DhcpTO> dhcpTos = cmd.getIps();
        final StringBuilder args = new StringBuilder();
        for (final DhcpTO dhcpTo : dhcpTos) {
            args.append(dhcpTo.getRouterIp());
            args.append(":");
            args.append(dhcpTo.getGateway());
            args.append(":");
            args.append(dhcpTo.getNetmask());
            args.append(":");
            args.append(dhcpTo.getStartIpOfSubnet());
            args.append("-");
        }

        try {
            final String command = String.format("%s%s %s", "/root/", VRScripts.DHCP, args);

            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Run command on domain router " + routerIp + ",  /root/dnsmasq.sh");
            }

            if (!result.first()) {
                s_logger.error("Unable update dnsmasq config file");
                return new Answer(cmd, false, "dnsmasq config update failed due to: " + result.second());
            }

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("dnsmasq config command on domain router " + routerIp + " completed");
            }
        } catch (final Throwable e) {
            final String msg = "Dnsmasqconfig command failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd);
    }

};