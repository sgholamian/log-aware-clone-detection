//,temp,CitrixResourceBase.java,1544,1563,temp,Xenserver625Resource.java,52,78
//,3
public class xxx {
    @Override
    public boolean setupServer(final Connection conn,final Host host) {
        final com.trilead.ssh2.Connection sshConnection = new com.trilead.ssh2.Connection(_host.getIp(), 22);
        try {
            sshConnection.connect(null, 60000, 60000);
            if (!sshConnection.authenticateWithPassword(_username, _password.peek())) {
                throw new CloudRuntimeException("Unable to authenticate");
            }

            final String cmd = "rm -f /opt/xensource/sm/hostvmstats.py " +
                    "/opt/xensource/bin/copy_vhd_to_secondarystorage.sh " +
                    "/opt/xensource/bin/copy_vhd_from_secondarystorage.sh " +
                    "/opt/xensource/bin/create_privatetemplate_from_snapshot.sh " +
                    "/opt/xensource/bin/vhd-util " +
                    "/opt/cloud/bin/copy_vhd_to_secondarystorage.sh " +
                    "/opt/cloud/bin/copy_vhd_from_secondarystorage.sh " +
                    "/opt/cloud/bin/create_privatetemplate_from_snapshot.sh " +
                    "/opt/cloud/bin/vhd-util";

            SSHCmdHelper.sshExecuteCmd(sshConnection, cmd);
        } catch (final Exception e) {
            s_logger.debug("Catch exception " + e.toString(), e);
        } finally {
            sshConnection.close();
        }
        return super.setupServer(conn, host);
    }

};