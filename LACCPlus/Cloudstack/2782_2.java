//,temp,BaremetalDnsmasqResource.java,46,92,temp,BaremetalKickStartPxeResource.java,47,100
//,3
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        super.configure(name, params);
        _tftpDir = (String)params.get(BaremetalPxeService.PXE_PARAM_TFTP_DIR);
        if (_tftpDir == null) {
            throw new ConfigurationException("No tftp directory specified");
        }

        com.trilead.ssh2.Connection sshConnection = new com.trilead.ssh2.Connection(_ip, 22);

        s_logger.debug(String.format("Trying to connect to kickstart PXE server(IP=%1$s, username=%2$s, password=%3$s", _ip, _username, "******"));
        try {
            sshConnection.connect(null, 60000, 60000);
            if (!sshConnection.authenticateWithPassword(_username, _password)) {
                s_logger.debug("SSH Failed to authenticate");
                throw new ConfigurationException(String.format("Cannot connect to kickstart PXE server(IP=%1$s, username=%2$s, password=%3$s", _ip, _username, "******"));
            }

            String cmd = String.format("[ -f /%1$s/pxelinux.0 ]", _tftpDir);
            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, cmd)) {
                throw new ConfigurationException("Miss files in TFTP directory at " + _tftpDir + " check if pxelinux.0 are here");
            }

            SCPClient scp = new SCPClient(sshConnection);
            String prepareScript = "scripts/network/ping/prepare_kickstart_bootfile.py";
            String prepareScriptPath = Script.findScript("", prepareScript);
            if (prepareScriptPath == null) {
                throw new ConfigurationException("Can not find prepare_kickstart_bootfile.py at " + prepareScript);
            }
            scp.put(prepareScriptPath, "/usr/bin/", "0755");

            String cpScript = "scripts/network/ping/prepare_kickstart_kernel_initrd.py";
            String cpScriptPath = Script.findScript("", cpScript);
            if (cpScriptPath == null) {
                throw new ConfigurationException("Can not find prepare_kickstart_kernel_initrd.py at " + cpScript);
            }
            scp.put(cpScriptPath, "/usr/bin/", "0755");

            String userDataScript = "scripts/network/ping/baremetal_user_data.py";
            String userDataScriptPath = Script.findScript("", userDataScript);
            if (userDataScriptPath == null) {
                throw new ConfigurationException("Can not find baremetal_user_data.py at " + userDataScript);
            }
            scp.put(userDataScriptPath, "/usr/bin/", "0755");

            return true;
        } catch (Exception e) {
            throw new CloudRuntimeException(e);
        } finally {
            if (sshConnection != null) {
                sshConnection.close();
            }
        }
    }

};