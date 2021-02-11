//,temp,BuildGuestNetwork.java,37,121,temp,PerformanceWithAPI.java,47,148
//,3
public class xxx {
    public static void main(String[] args) {

        List<String> argsList = Arrays.asList(args);
        Iterator<String> iter = argsList.iterator();
        String host = "http://localhost";
        int numThreads = 1;

        while (iter.hasNext()) {
            String arg = iter.next();
            if (arg.equals("-h")) {
                host = "http://" + iter.next();
            }
            if (arg.equals("-t")) {
                numThreads = Integer.parseInt(iter.next());
            }
            if (arg.equals("-n")) {
                s_numVM = Integer.parseInt(iter.next());
            }
        }

        final String server = host + ":" + ApiPort + "/";
        final String developerServer = host + ":" + DeveloperPort + ApiUrl;

        s_logger.info("Starting test in " + numThreads + " thread(s). Each thread is launching " + s_numVM + " VMs");

        for (int i = 0; i < numThreads; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String username = null;
                        String singlePrivateIp = null;
                        String singlePublicIp = null;
                        Random ran = new Random();
                        username = Math.abs(ran.nextInt()) + "-user";

                        //Create User
                        User myUser = new User(username, username, server, developerServer);
                        try {
                            myUser.launchUser();
                            myUser.registerUser();
                        } catch (Exception e) {
                            s_logger.warn("Error code: ", e);
                        }

                        if (myUser.getUserId() != null) {
                            s_logger.info("User " + myUser.getUserName() + " was created successfully, starting VM creation");
                            //create VMs for the user
                            for (int i = 0; i < s_numVM; i++) {
                                //Create a new VM, add it to the list of user's VMs
                                VirtualMachine myVM = new VirtualMachine(myUser.getUserId());
                                myVM.deployVM(ZoneId, ServiceOfferingId, TemplateId, myUser.getDeveloperServer(), myUser.getApiKey(), myUser.getSecretKey());
                                myUser.getVirtualMachines().add(myVM);
                                singlePrivateIp = myVM.getPrivateIp();

                                if (singlePrivateIp != null) {
                                    s_logger.info("VM with private Ip " + singlePrivateIp + " was successfully created");
                                } else {
                                    s_logger.info("Problems with VM creation for a user" + myUser.getUserName());
                                    break;
                                }

                                //get public IP address for the User
                                myUser.retrievePublicIp(ZoneId);
                                singlePublicIp = myUser.getPublicIp().get(myUser.getPublicIp().size() - 1);
                                if (singlePublicIp != null) {
                                    s_logger.info("Successfully got public Ip " + singlePublicIp + " for user " + myUser.getUserName());
                                } else {
                                    s_logger.info("Problems with getting public Ip address for user" + myUser.getUserName());
                                    break;
                                }

                                //create ForwardProxy rules for user's VMs
                                int responseCode = CreateForwardingRule(myUser, singlePrivateIp, singlePublicIp, "22", "22");
                                if (responseCode == 500)
                                    break;
                            }

                            s_logger.info("Deployment successful..." + s_numVM + " VMs were created. Waiting for 5 min before performance test");
                            Thread.sleep(300000L); // Wait

                            //Start performance test for the user
                            s_logger.info("Starting performance test for Guest network that has " + myUser.getPublicIp().size() + " public IP addresses");
                            for (int j = 0; j < myUser.getPublicIp().size(); j++) {
                                s_logger.info("Starting test for user which has " + myUser.getVirtualMachines().size() + " vms. Public IP for the user is " +
                                    myUser.getPublicIp().get(j) + " , number of retries is " + Retry + " , private IP address of the machine is" +
                                    myUser.getVirtualMachines().get(j).getPrivateIp());
                                GuestNetwork myNetwork = new GuestNetwork(myUser.getPublicIp().get(j), Retry);
                                myNetwork.setVirtualMachines(myUser.getVirtualMachines());
                                new Thread(myNetwork).start();
                            }

                        }
                    } catch (Exception e) {
                        s_logger.error(e);
                    }
                }
            }).start();

        }
    }

};