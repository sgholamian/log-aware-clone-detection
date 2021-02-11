//,temp,CitrixResourceBase.java,1544,1563,temp,XenServer56Resource.java,102,123
//,3
public class xxx {
    public Boolean checkHeartbeat(final String hostuuid) {
        final com.trilead.ssh2.Connection sshConnection = new com.trilead.ssh2.Connection(_host.getIp(), 22);
        try {
            sshConnection.connect(null, 60000, 60000);
            if (!sshConnection.authenticateWithPassword(_username, _password.peek())) {
                throw new CloudRuntimeException("Unable to authenticate");
            }

            final String shcmd = "/opt/cloud/bin/check_heartbeat.sh " + hostuuid + " " + Integer.toString(_heartbeatInterval * 2);
            if (!SSHCmdHelper.sshExecuteCmd(sshConnection, shcmd)) {
                s_logger.debug("Heart beat is gone so dead.");
                return false;
            }
            s_logger.debug("Heart beat is still going");
            return true;
        } catch (final Exception e) {
            s_logger.debug("health check failed due to catch exception " + e.toString());
            return null;
        } finally {
            sshConnection.close();
        }
    }

};