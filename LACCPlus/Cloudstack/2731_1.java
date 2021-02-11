//,temp,TestClient.java,112,207,temp,StressTestDirectAttach.java,161,272
//,3
public class xxx {
                    @Override
                    public void run() {
                        do {
                            String username = null;
                            try {
                                long now = System.currentTimeMillis();
                                Random ran = new Random();
                                if (users != null) {
                                    username = users[Math.abs(ran.nextInt()) % numOfUsers];
                                } else {
                                    username = Math.abs(ran.nextInt()) + "-user";
                                }
                                NDC.push(username);

                                String url = server + "?email=" + username + "&password=" + username + "&command=deploy";
                                s_logger.info("Launching test for user: " + username + " with url: " + url);
                                HttpClient client = new HttpClient();
                                HttpMethod method = new GetMethod(url);
                                int responseCode = client.executeMethod(method);
                                boolean success = false;
                                String reason = null;
                                if (responseCode == 200) {
                                    if (internet) {
                                        s_logger.info("Deploy successful...waiting 5 minute before SSH tests");
                                        Thread.sleep(300000L);  // Wait 60 seconds so the linux VM can boot up.

                                        s_logger.info("Begin Linux SSH test");
                                        reason = sshTest(method.getResponseHeader("linuxIP").getValue());

                                        if (reason == null) {
                                            s_logger.info("Linux SSH test successful");
                                            s_logger.info("Begin Windows SSH test");
                                            reason = sshWinTest(method.getResponseHeader("windowsIP").getValue());
                                        }
                                    }
                                    if (reason == null) {
                                        if (internet) {
                                            s_logger.info("Windows SSH test successful");
                                        } else {
                                            s_logger.info("deploy test successful....now cleaning up");
                                            if (cleanUp) {
                                                s_logger.info("Waiting " + sleepTime + " ms before cleaning up vms");
                                                Thread.sleep(sleepTime);
                                            } else {
                                                success = true;
                                            }
                                        }
                                        if (users == null) {
                                            s_logger.info("Sending cleanup command");
                                            url = server + "?email=" + username + "&password=" + username + "&command=cleanup";
                                        } else {
                                            s_logger.info("Sending stop DomR / destroy VM command");
                                            url = server + "?email=" + username + "&password=" + username + "&command=stopDomR";
                                        }
                                        method = new GetMethod(url);
                                        responseCode = client.executeMethod(method);
                                        if (responseCode == 200) {
                                            success = true;
                                        } else {
                                            reason = method.getStatusText();
                                        }
                                    } else {
                                        // Just stop but don't destroy the VMs/Routers
                                        s_logger.info("SSH test failed with reason '" + reason + "', stopping VMs");
                                        url = server + "?email=" + username + "&password=" + username + "&command=stop";
                                        responseCode = client.executeMethod(new GetMethod(url));
                                    }
                                } else {
                                    // Just stop but don't destroy the VMs/Routers
                                    reason = method.getStatusText();
                                    s_logger.info("Deploy test failed with reason '" + reason + "', stopping VMs");
                                    url = server + "?email=" + username + "&password=" + username + "&command=stop";
                                    client.executeMethod(new GetMethod(url));
                                }

                                if (success) {
                                    s_logger.info("***** Completed test for user : " + username + " in " + ((System.currentTimeMillis() - now) / 1000L) + " seconds");
                                } else {
                                    s_logger.info("##### FAILED test for user : " + username + " in " + ((System.currentTimeMillis() - now) / 1000L) +
                                        " seconds with reason : " + reason);
                                }
                            } catch (Exception e) {
                                s_logger.warn("Error in thread", e);
                                try {
                                    HttpClient client = new HttpClient();
                                    String url = server + "?email=" + username + "&password=" + username + "&command=stop";
                                    client.executeMethod(new GetMethod(url));
                                } catch (Exception e1) {
                                    s_logger.info("[ignored]"
                                            + "error while executing last resort stop attampt: " + e1.getLocalizedMessage());
                                }
                            } finally {
                                NDC.clear();
                            }
                        } while (repeat);
                    }

};