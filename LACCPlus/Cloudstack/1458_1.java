//,temp,TestClientWithAPI.java,108,363,temp,TestClient.java,48,213
//,3
public class xxx {
    public static void main(String[] args) {
        String host = "http://localhost";
        String port = "8092";
        String devPort = "8080";
        String apiUrl = "/client/api";

        try {
            // Parameters
            List<String> argsList = Arrays.asList(args);
            Iterator<String> iter = argsList.iterator();
            while (iter.hasNext()) {
                String arg = iter.next();
                // host
                if (arg.equals("-h")) {
                    host = "http://" + iter.next();
                }

                if (arg.equals("-p")) {
                    port = iter.next();
                }
                if (arg.equals("-dp")) {
                    devPort = iter.next();
                }

                if (arg.equals("-t")) {
                    numThreads = Integer.parseInt(iter.next());
                }

                if (arg.equals("-s")) {
                    sleepTime = Long.parseLong(iter.next());
                }
                if (arg.equals("-a")) {
                    accountName = iter.next();
                }

                if (arg.equals("-c")) {
                    cleanUp = Boolean.parseBoolean(iter.next());
                    if (!cleanUp)
                        sleepTime = 0L; // no need to wait if we don't ever
                    // cleanup
                }

                if (arg.equals("-r")) {
                    repeat = Boolean.parseBoolean(iter.next());
                }

                if (arg.equals("-u")) {
                    numOfUsers = Integer.parseInt(iter.next());
                }

                if (arg.equals("-i")) {
                    internet = Boolean.parseBoolean(iter.next());
                }

                if (arg.equals("-w")) {
                    wait = Integer.parseInt(iter.next());
                }

                if (arg.equals("-z")) {
                    zoneId = iter.next();
                }

                if (arg.equals("-snapshot")) {
                    snapshotTest = "yes";
                }

                if (arg.equals("-so")) {
                    serviceOfferingId = iter.next();
                }

                if (arg.equals("-do")) {
                    diskOfferingId = iter.next();
                }

                if (arg.equals("-no")) {
                    networkOfferingId = iter.next();
                }

                if (arg.equals("-pass")) {
                    vmPassword = iter.next();
                }

                if (arg.equals("-url")) {
                    downloadUrl = iter.next();
                }

            }

            final String server = host + ":" + port + "/";
            final String developerServer = host + ":" + devPort + apiUrl;
            s_logger.info("Starting test against server: " + server + " with " + numThreads + " thread(s)");
            if (cleanUp)
                s_logger.info("Clean up is enabled, each test will wait " + sleepTime + " ms before cleaning up");

            if (numOfUsers > 0) {
                s_logger.info("Pre-generating users for test of size : " + numOfUsers);
                users = new String[numOfUsers];
                Random ran = new Random();
                for (int i = 0; i < numOfUsers; i++) {
                    users[i] = Math.abs(ran.nextInt()) + "-user";
                }
            }

            for (int i = 0; i < numThreads; i++) {
                new Thread(new Runnable() {
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

                                s_logger.info("Starting test for the user " + username);
                                int response = executeDeployment(server, developerServer, username, snapshotTest);
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

                                        if (accountName == null) {
                                            s_logger.info("Begin Linux SSH test for account " + s_account.get());
                                            reason = sshTest(s_linuxIP.get(), s_linuxPassword.get(), snapshotTest);
                                        }

                                        if (reason == null) {
                                            s_logger.info("Linux SSH test successful for account " + s_account.get());
                                            s_logger.info("Begin WindowsSSH test for account " + s_account.get());

                                            reason = sshTest(s_linuxIP.get(), s_linuxPassword.get(), snapshotTest);
                                            // reason = sshWinTest(s_windowsIP.get());
                                        }

                                        // release the linux IP now...
                                        s_linuxIP.set(null);
                                        // release the Windows IP now
                                        s_windowsIP.set(null);
                                    }

                                    // sleep for 3 min before getting the latest network stat
                                    // s_logger.info("Sleeping for 5 min before getting the lates network stat for the account");
                                    // Thread.sleep(300000);
                                    // verify that network stat is correct for the user; if it's not - stop all the resources
                                    // for the user
                                    // if ((reason == null) && (getNetworkStat(server) == false) ) {
                                    // s_logger.error("Stopping all the resources for the account " + s_account.get() +
                                    // " as network stat is incorrect");
                                    // int stopResponseCode = executeStop(
                                    // server, developerServer,
                                    // username, false);
                                    // s_logger
                                    // .info("stop command finished with response code: "
                                    // + stopResponseCode);
                                    // success = false; // since the SSH test
                                    //
                                    // } else
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
                                            int stopResponseCode = executeStop(server, developerServer, username, true);
                                            s_logger.info("stop(destroy) command finished with response code: " + stopResponseCode);
                                            success = (stopResponseCode == 200);
                                        }

                                    } else {
                                        // Just stop but don't destroy the
                                        // VMs/Routers
                                        s_logger.info("SSH test failed for account " + s_account.get() + "with reason '" + reason + "', stopping VMs");
                                        int stopResponseCode = executeStop(server, developerServer, username, false);
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
                                    int stopResponseCode = executeStop(server, developerServer, username, true);
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
                                    int stopResponseCode = executeStop(server, developerServer, username, true);
                                    s_logger.info("stop response code: " + stopResponseCode);
                                } catch (Exception e1) {
                                    s_logger.info("[ignored]"
                                            + "error executing stop during api test: " + e1.getLocalizedMessage());
                                }
                            } finally {
                                NDC.clear();
                            }
                        } while (repeat);
                    }
                }).start();
            }
        } catch (Exception e) {
            s_logger.error(e);
        }
    }

};