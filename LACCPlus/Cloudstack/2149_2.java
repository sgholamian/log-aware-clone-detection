//,temp,TestClientWithAPI.java,2112,2220,temp,TestClient.java,215,299
//,3
public class xxx {
    private static String sshWinTest(String host) {
        if (host == null) {
            s_logger.info("Did not receive a host back from test, ignoring win ssh test");
            return null;
        }

        // We will retry 5 times before quitting
        int retry = 0;

        while (true) {
            try {
                if (retry > 0) {
                    s_logger.info("Retry attempt : " + retry + " ...sleeping 300 seconds before next attempt");
                    Thread.sleep(300000);
                }

                s_logger.info("Attempting to SSH into windows host " + host + " with retry attempt: " + retry);

                Connection conn = new Connection(host);
                conn.connect(null, 60000, 60000);

                s_logger.info("SSHed successfully into windows host " + host);
                boolean success = false;
                boolean isAuthenticated = conn.authenticateWithPassword("vmops", "vmops");
                if (isAuthenticated == false) {
                    return "Authentication failed";
                }
                SCPClient scp = new SCPClient(conn);

                scp.put("wget.exe", "");

                Session sess = conn.openSession();
                s_logger.info("Executing : wget http://172.16.0.220/dump.bin");
                sess.execCommand("wget http://172.16.0.220/dump.bin && dir dump.bin");

                InputStream stdout = sess.getStdout();
                InputStream stderr = sess.getStderr();

                byte[] buffer = new byte[8192];
                while (true) {
                    if ((stdout.available() == 0) && (stderr.available() == 0)) {
                        int conditions = sess.waitForCondition(ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA | ChannelCondition.EOF, 120000);

                        if ((conditions & ChannelCondition.TIMEOUT) != 0) {
                            s_logger.info("Timeout while waiting for data from peer.");
                            return null;
                        }

                        if ((conditions & ChannelCondition.EOF) != 0) {
                            if ((conditions & (ChannelCondition.STDOUT_DATA | ChannelCondition.STDERR_DATA)) == 0) {
                                break;
                            }
                        }
                    }

                    while (stdout.available() > 0) {
                        success = true;
                        int len = stdout.read(buffer);
                        if (len > 0) // this check is somewhat paranoid
                            s_logger.info(new String(buffer, 0, len));
                    }

                    while (stderr.available() > 0) {
                        int len = stderr.read(buffer);
                    }
                }
                sess.close();
                conn.close();

                if (success) {
                    return null;
                } else {
                    retry++;
                    if (retry == MAX_RETRY_WIN) {
                        return "SSH Windows Network test fail";
                    }
                }
            } catch (Exception e) {
                retry++;
                if (retry == MAX_RETRY_WIN) {
                    return "SSH Windows Network test fail with error " + e.getMessage();
                }
            }
        }
    }

};