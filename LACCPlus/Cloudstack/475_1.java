//,temp,SshHelper.java,97,130,temp,SshHelper.java,61,95
//,3
public class xxx {
    public static void scpTo(String host, int port, String user, File pemKeyFile, String password, String remoteTargetDirectory, byte[] data, String remoteFileName,
            String fileMode, int connectTimeoutInMs, int kexTimeoutInMs) throws Exception {

        com.trilead.ssh2.Connection conn = null;
        com.trilead.ssh2.SCPClient scpClient = null;

        try {
            conn = new com.trilead.ssh2.Connection(host, port);
            conn.connect(null, connectTimeoutInMs, kexTimeoutInMs);

            if (pemKeyFile == null) {
                if (!conn.authenticateWithPassword(user, password)) {
                    String msg = "Failed to authentication SSH user " + user + " on host " + host;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }
            } else {
                if (!conn.authenticateWithPublicKey(user, pemKeyFile, password)) {
                    String msg = "Failed to authentication SSH user " + user + " on host " + host;
                    s_logger.error(msg);
                    throw new Exception(msg);
                }
            }

            scpClient = conn.createSCPClient();
            if (fileMode != null)
                scpClient.put(data, remoteFileName, remoteTargetDirectory, fileMode);
            else
                scpClient.put(data, remoteFileName, remoteTargetDirectory);
        } finally {
            if (conn != null)
                conn.close();
        }
    }

};