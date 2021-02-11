//,temp,HypervDirectConnectResource.java,1511,1572,temp,HypervDirectConnectResource.java,1370,1409
//,3
public class xxx {
    protected Answer execute(final DhcpEntryCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource DhcpEntryCommand: " + s_gson.toJson(cmd));
        }

        // ssh -p 3922 -o StrictHostKeyChecking=no -i $cert root@$domr "/root/edithosts.sh $mac $ip $vm $dfltrt $ns $staticrt" >/dev/null

        String args = " -m " + cmd.getVmMac();
        if (cmd.getVmIpAddress() != null) {
            args += " -4 " + cmd.getVmIpAddress();
        }
        args += " -h " + cmd.getVmName();

        if (cmd.getDefaultRouter() != null) {
            args += " -d " + cmd.getDefaultRouter();
        }

        if (cmd.getDefaultDns() != null) {
            args += " -n " + cmd.getDefaultDns();
        }

        if (cmd.getStaticRoutes() != null) {
            args += " -s " + cmd.getStaticRoutes();
        }

        if (cmd.getVmIp6Address() != null) {
            args += " -6 " + cmd.getVmIp6Address();
            args += " -u " + cmd.getDuid();
        }

        if (!cmd.isDefault()) {
            args += " -N";
        }

        final String command = String.format("%s%s %s", "/root/", VRScripts.DHCP, args);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Run command on domR " + cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP) + command);
        }

        try {
            final String controlIp = getRouterSshControlIp(cmd);
            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                s_logger.error("dhcp_entry command on domR " + controlIp + " failed, message: " + result.second());

                return new Answer(cmd, false, "DhcpEntry failed due to " + result.second());
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("dhcp_entry command on domain router " + controlIp + " completed");
            }

        } catch (final Throwable e) {
            final String msg = "DhcpEntryCommand failed due to " + e;
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd);
    }

};