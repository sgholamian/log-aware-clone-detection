//,temp,TestClient.java,301,384,temp,StressTestDirectAttach.java,1182,1284
//,3
public class xxx {
    private static String sshTest(String host, String password) {
        int i = 0;
        if (host == null) {
            s_logger.info("Did not receive a host back from test, ignoring ssh test");
            return null;
        }

        if (password == null) {
            s_logger.info("Did not receive a password back from test, ignoring ssh test");
            return null;
        }

        // We will retry 5 times before quitting
        String result = null;
        int retry = 0;

        while (true) {
            try {
                if (retry > 0) {
                    s_logger.info("Retry attempt : " + retry + " ...sleeping 120 seconds before next attempt. Account is " + s_account.get());
                    Thread.sleep(120000);
                }

                s_logger.info("Attempting to SSH into linux host " + host + " with retry attempt: " + retry + ". Account is " + s_account.get());

                Connection conn = new Connection(host);
                conn.connect(null, 60000, 60000);

                s_logger.info("User + " + s_account.get() + " ssHed successfully into linux host " + host);

                boolean isAuthenticated = conn.authenticateWithPassword("root", password);

                if (isAuthenticated == false) {
                    s_logger.info("Authentication failed for root with password" + password);
                    return "Authentication failed";

                }

                boolean success = false;
                String linuxCommand = null;

                if (i % 10 == 0)
                    linuxCommand = "rm -rf *; wget http://192.168.1.250/dump.bin && ls -al dump.bin";
                else
                    linuxCommand = "wget http://192.168.1.250/dump.bin && ls -al dump.bin";

                Session sess = conn.openSession();
                s_logger.info("User " + s_account.get() + " executing : " + linuxCommand);
                sess.execCommand(linuxCommand);

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

                if (!success) {
                    retry++;
                    if (retry == MAX_RETRY_LINUX) {
                        result = "SSH Linux Network test fail";
                    }
                }

                return result;
            } catch (Exception e) {
                retry++;
                s_logger.error("SSH Linux Network test fail with error");
                if (retry == MAX_RETRY_LINUX) {
                    return "SSH Linux Network test fail with error " + e.getMessage();
                }
            }
            i++;
        }
    }

};