//,temp,BaremetalPingPxeResource.java,177,203,temp,BaremetalPingPxeResource.java,149,175
//,3
public class xxx {
    protected PreparePxeServerAnswer execute(PreparePxeServerCommand cmd) {
        com.trilead.ssh2.Connection sshConnection = new com.trilead.ssh2.Connection(_ip, 22);
        try {
            sshConnection.connect(null, 60000, 60000);
            if (!sshConnection.authenticateWithPassword(_username, _password)) {
                s_logger.debug("SSH Failed to authenticate");
                throw new ConfigurationException(String.format("Cannot connect to PING PXE server(IP=%1$s, username=%2$s, password=%3$s", _ip, _username, _password));
            }

            String script =
                String.format("python /usr/bin/prepare_tftp_bootfile.py restore %1$s %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s %10$s %11$s", _tftpDir, cmd.getMac(),
                    _storageServer, _share, _dir, cmd.getTemplate(), _cifsUserName, _cifsPassword, cmd.getIp(), cmd.getNetMask(), cmd.getGateWay());
            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, script)) {
                return new PreparePxeServerAnswer(cmd, "prepare PING at " + _ip + " failed, command:" + script);
            }
            s_logger.debug("Prepare Ping PXE server successfully");

            return new PreparePxeServerAnswer(cmd);
        } catch (Exception e) {
            s_logger.debug("Prepare PING pxe server failed", e);
            return new PreparePxeServerAnswer(cmd, e.getMessage());
        } finally {
            if (sshConnection != null) {
                sshConnection.close();
            }
        }
    }

};