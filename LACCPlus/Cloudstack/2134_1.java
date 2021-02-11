//,temp,BaremetalDnsmasqResource.java,46,92,temp,BaremetalDhcpdResource.java,46,100
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        com.trilead.ssh2.Connection sshConnection = null;
        try {
            super.configure(name, params);
            s_logger.debug(String.format("Trying to connect to DHCP server(IP=%1$s, username=%2$s, password=%3$s)", _ip, _username, _password));
            sshConnection = SSHCmdHelper.acquireAuthorizedConnection(_ip, _username, _password);
            if (sshConnection == null) {
                throw new ConfigurationException(String.format("Cannot connect to DHCP server(IP=%1$s, username=%2$s, password=%3$s", _ip, _username, _password));
            }

            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, "[ -f '/usr/sbin/dnsmasq' ]")) {
                throw new ConfigurationException("Cannot find dnsmasq at /usr/sbin/dnsmasq on " + _ip);
            }

            SCPClient scp = new SCPClient(sshConnection);

            String editHosts = "scripts/network/exdhcp/dnsmasq_edithosts.sh";
            String editHostsPath = Script.findScript("", editHosts);
            if (editHostsPath == null) {
                throw new ConfigurationException("Can not find script dnsmasq_edithosts.sh at " + editHosts);
            }
            scp.put(editHostsPath, "/usr/bin/", "0755");

            String prepareDnsmasq = "scripts/network/exdhcp/prepare_dnsmasq.sh";
            String prepareDnsmasqPath = Script.findScript("", prepareDnsmasq);
            if (prepareDnsmasqPath == null) {
                throw new ConfigurationException("Can not find script prepare_dnsmasq.sh at " + prepareDnsmasq);
            }
            scp.put(prepareDnsmasqPath, "/usr/bin/", "0755");

            /*
            String prepareCmd = String.format("sh /usr/bin/prepare_dnsmasq.sh %1$s %2$s %3$s", _gateway, _dns, _ip);
            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, prepareCmd)) {
                throw new ConfigurationException("prepare dnsmasq at " + _ip + " failed");
            }
            */

            s_logger.debug("Dnsmasq resource configure successfully");
            return true;
        } catch (Exception e) {
            s_logger.debug("Dnsmasq resorce configure failed", e);
            throw new ConfigurationException(e.getMessage());
        } finally {
            SSHCmdHelper.releaseSshConnection(sshConnection);
        }
    }

};