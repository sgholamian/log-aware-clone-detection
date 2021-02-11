//,temp,BaremetalKickStartPxeResource.java,113,153,temp,BaremetalPingPxeResource.java,218,258
//,3
public class xxx {
    private Answer execute(VmDataCommand cmd) {
        com.trilead.ssh2.Connection sshConnection = new com.trilead.ssh2.Connection(_ip, 22);
        try {
            List<String[]> vmData = cmd.getVmData();
            StringBuilder sb = new StringBuilder();
            for (String[] data : vmData) {
                String folder = data[0];
                String file = data[1];
                String contents = (data[2] == null) ? "none" : data[2];
                sb.append(cmd.getVmIpAddress());
                sb.append(",");
                sb.append(folder);
                sb.append(",");
                sb.append(file);
                sb.append(",");
                sb.append(contents);
                sb.append(";");
            }
            String arg = org.apache.commons.lang.StringUtils.stripEnd(sb.toString(), ";");

            sshConnection.connect(null, 60000, 60000);
            if (!sshConnection.authenticateWithPassword(_username, _password)) {
                s_logger.debug("SSH Failed to authenticate");
                throw new ConfigurationException(String.format("Cannot connect to PING PXE server(IP=%1$s, username=%2$s, password=%3$s", _ip, _username, _password));
            }

            String script = String.format("python /usr/bin/baremetal_user_data.py '%s'", arg);
            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, script)) {
                return new Answer(cmd, false, "Failed to add user data, command:" + script);
            }

            return new Answer(cmd, true, "Success");
        } catch (Exception e) {
            s_logger.debug("Prepare for creating baremetal template failed", e);
            return new Answer(cmd, false, e.getMessage());
        } finally {
            if (sshConnection != null) {
                sshConnection.close();
            }
        }
    }

};