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
                                username = Math.abs(ran.nextInt()) + "-user";
                                NDC.push(username);

                                s_logger.info("Starting test for the user " + username);
                                int response = executeDeployment(server, developerServer, username);
                                boolean success = false;
                                String reason = null;

                                if (response == 200) {
                                    success = true;
                                    if (internet) {
                                        s_logger.info("Deploy successful...waiting 5 minute before SSH tests");
                                        Thread.sleep(300000L); // Wait 60
                                        // seconds so
                                        // the windows VM
                                        // can boot up and do a sys prep.

                                        s_logger.info("Begin Linux SSH test for account " + s_account.get());
                                        reason = sshTest(s_linuxIP.get(), s_linuxPassword.get());

                                        if (reason == null) {
                                            s_logger.info("Linux SSH test successful for account " + s_account.get());
                                        }
                                    }
                                    if (reason == null) {
                                        if (internet) {
                                            s_logger.info("Windows SSH test successful for account " + s_account.get());
                                        } else {
                                            s_logger.info("deploy test successful....now cleaning up");
                                            if (cleanUp) {
                                                s_logger.info("Waiting " + sleepTime + " ms before cleaning up vms");
                                                Thread.sleep(sleepTime);
                                            } else {
                                                success = true;
                                            }
                                        }

                                        if (usageIterator >= numThreads) {
                                            int eventsAndBillingResponseCode = executeEventsAndBilling(server, developerServer);
                                            s_logger.info("events and usage records command finished with response code: " + eventsAndBillingResponseCode);
                                            usageIterator = 1;

                                        } else {
                                            s_logger.info("Skipping events and usage records for this user: usageIterator " + usageIterator + " and number of Threads " +
                                                numThreads);
                                            usageIterator++;
                                        }

                                        if ((users == null) && (accountName == null)) {
                                            s_logger.info("Sending cleanup command");
                                            int cleanupResponseCode = executeCleanup(server, developerServer, username);
                                            s_logger.info("cleanup command finished with response code: " + cleanupResponseCode);
                                            success = (cleanupResponseCode == 200);
                                        } else {
                                            s_logger.info("Sending stop DomR / destroy VM command");
                                            int stopResponseCode = executeStop(server, developerServer, username);
                                            s_logger.info("stop(destroy) command finished with response code: " + stopResponseCode);
                                            success = (stopResponseCode == 200);
                                        }

                                    } else {
                                        // Just stop but don't destroy the
                                        // VMs/Routers
                                        s_logger.info("SSH test failed for account " + s_account.get() + "with reason '" + reason + "', stopping VMs");
                                        int stopResponseCode = executeStop(server, developerServer, username);
                                        s_logger.info("stop command finished with response code: " + stopResponseCode);
                                        success = false; // since the SSH test
                                        // failed, mark the
                                        // whole test as
                                        // failure
                                    }
                                } else {
                                    // Just stop but don't destroy the
                                    // VMs/Routers
                                    s_logger.info("Deploy test failed with reason '" + reason + "', stopping VMs");
                                    int stopResponseCode = executeStop(server, developerServer, username);
                                    s_logger.info("stop command finished with response code: " + stopResponseCode);
                                    success = false; // since the deploy test
                                    // failed, mark the
                                    // whole test as failure
                                }

                                if (success) {
                                    s_logger.info("***** Completed test for user : " + username + " in " + ((System.currentTimeMillis() - now) / 1000L) + " seconds");

                                } else {
                                    s_logger.info("##### FAILED test for user : " + username + " in " + ((System.currentTimeMillis() - now) / 1000L) +
                                        " seconds with reason : " + reason);
                                }
                                s_logger.info("Sleeping for " + wait + " seconds before starting next iteration");
                                Thread.sleep(wait);
                            } catch (Exception e) {
                                s_logger.warn("Error in thread", e);
                                try {
                                    int stopResponseCode = executeStop(server, developerServer, username);
                                    s_logger.info("stop response code: " + stopResponseCode);
                                } catch (Exception e1) {
                                    s_logger.info("[ignored]"
                                            + "error executing stop during stress test: " + e1.getLocalizedMessage());
                                }
                            } finally {
                                NDC.clear();
                            }
                        } while (repeat);
                    }

};