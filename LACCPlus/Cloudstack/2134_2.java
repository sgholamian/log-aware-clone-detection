//,temp,BaremetalDnsmasqResource.java,46,92,temp,BaremetalDhcpdResource.java,46,100
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        com.trilead.ssh2.Connection sshConnection = null;
        try {
            super.configure(name, params);
            s_logger.debug(String.format("Trying to connect to DHCP server(IP=%1$s, username=%2$s, password=%3$s)", _ip, _username, "******"));
            sshConnection = SSHCmdHelper.acquireAuthorizedConnection(_ip, _username, _password);
            if (sshConnection == null) {
                throw new ConfigurationException(String.format("Cannot connect to DHCP server(IP=%1$s, username=%2$s, password=%3$s", _ip, _username, "******"));
            }

            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, "[ -f '/usr/sbin/dhcpd' ]")) {
                throw new ConfigurationException("Cannot find dhcpd.conf /etc/dhcpd.conf at  on " + _ip);
            }

            SCPClient scp = new SCPClient(sshConnection);

            String editHosts = "scripts/network/exdhcp/dhcpd_edithosts.py";
            String editHostsPath = Script.findScript("", editHosts);
            if (editHostsPath == null) {
                throw new ConfigurationException("Can not find script dnsmasq_edithosts.sh at " + editHosts);
            }
            scp.put(editHostsPath, "/usr/bin/", "0755");

            String prepareDhcpdScript = "scripts/network/exdhcp/prepare_dhcpd.sh";
            String prepareDhcpdScriptPath = Script.findScript("", prepareDhcpdScript);
            if (prepareDhcpdScriptPath == null) {
                throw new ConfigurationException("Can not find prepare_dhcpd.sh at " + prepareDhcpdScriptPath);
            }
            scp.put(prepareDhcpdScriptPath, "/usr/bin/", "0755");

            //TODO: tooooooooooooooo ugly here!!!
            String[] ips = _ip.split("\\.");
            ips[3] = "0";
            StringBuffer buf = new StringBuffer();
            int i;
            for (i = 0; i < ips.length - 1; i++) {
                buf.append(ips[i]).append(".");
            }
            buf.append(ips[i]);
            String subnet = buf.toString();
            String cmd = String.format("sh /usr/bin/prepare_dhcpd.sh %1$s", subnet);
            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, cmd)) {
                throw new ConfigurationException("prepare Dhcpd at " + _ip + " failed, command:" + cmd);
            }

            s_logger.debug("Dhcpd resource configure successfully");
            return true;
        } catch (Exception e) {
            s_logger.debug("Dhcpd resource configure failed", e);
            throw new ConfigurationException(e.getMessage());
        } finally {
            SSHCmdHelper.releaseSshConnection(sshConnection);
        }
    }

};