//,temp,TestClient.java,301,384,temp,StressTestDirectAttach.java,1083,1180
//,3
public class xxx {
    private static String sshWinTest(String host) {
        if (host == null) {
            s_logger.info("Did not receive a host back from test, ignoring win ssh test");
            return null;
        }

        // We will retry 5 times before quitting
        int retry = 1;

        while (true) {
            try {
                if (retry > 0) {
                    s_logger.info("Retry attempt : " + retry + " ...sleeping 300 seconds before next attempt. Account is " + s_account.get());
                    Thread.sleep(300000);
                }

                s_logger.info("Attempting to SSH into windows host " + host + " with retry attempt: " + retry + " for account " + s_account.get());

                Connection conn = new Connection(host);
                conn.connect(null, 60000, 60000);

                s_logger.info("User " + s_account.get() + " ssHed successfully into windows host " + host);
                boolean success = false;
                boolean isAuthenticated = conn.authenticateWithPassword("Administrator", "password");
                if (isAuthenticated == false) {
                    return "Authentication failed";
                } else {
                    s_logger.info("Authentication is successfull");
                }

                try {
                    SCPClient scp = new SCPClient(conn);
                    scp.put("wget.exe", "wget.exe", "C:\\Users\\Administrator", "0777");
                    s_logger.info("Successfully put wget.exe file");
                } catch (Exception ex) {
                    s_logger.error("Unable to put wget.exe " + ex);
                }

                if (conn == null) {
                    s_logger.error("Connection is null");
                }
                Session sess = conn.openSession();

                s_logger.info("User + " + s_account.get() + " executing : wget http://192.168.1.250/dump.bin");
                sess.execCommand("wget http://192.168.1.250/dump.bin && dir dump.bin");

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
                        /* int len = */stderr.read(buffer);
                    }
                }
                sess.close();
                conn.close();

                if (success) {
                    Thread.sleep(120000);
                    return null;
                } else {
                    retry++;
                    if (retry == MAX_RETRY_WIN) {
                        return "SSH Windows Network test fail for account " + s_account.get();
                    }
                }
            } catch (Exception e) {
                s_logger.error(e);
                retry++;
                if (retry == MAX_RETRY_WIN) {
                    return "SSH Windows Network test fail with error " + e.getMessage();
                }
            }
        }
    }

};