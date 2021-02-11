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
                numVM = Integer.parseInt(iter.next());
            }
            if (arg.equals("-z")) {
                zoneId = Integer.parseInt(iter.next());
            }

            if (arg.equals("-e")) {
                templateId = Integer.parseInt(iter.next());
            }

            if (arg.equals("-s")) {
                serviceOfferingId = Integer.parseInt(iter.next());
            }
        }

        final String server = host + ":" + ApiPort + "/";
        final String developerServer = host + ":" + DeveloperPort + ApiUrl;
        s_logger.info("Starting test in " + numThreads + " thread(s). Each thread is launching " + numVM + " VMs");

        for (int i = 0; i < numThreads; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String username = null;
                        String singlePrivateIp = null;
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
                            for (int i = 0; i < numVM; i++) {
                                //Create a new VM, add it to the list of user's VMs
                                VirtualMachine myVM = new VirtualMachine(myUser.getUserId());
                                myVM.deployVM(zoneId, serviceOfferingId, templateId, myUser.getDeveloperServer(), myUser.getApiKey(), myUser.getSecretKey());
                                myUser.getVirtualMachines().add(myVM);
                                singlePrivateIp = myVM.getPrivateIp();

                                if (singlePrivateIp != null) {
                                    s_logger.info("VM with private Ip " + singlePrivateIp + " was successfully created");
                                } else {
                                    s_logger.info("Problems with VM creation for a user" + myUser.getUserName());
                                    s_logger.info("Deployment failed");
                                    break;
                                }
                            }

                            s_logger.info("Deployment done..." + numVM + " VMs were created.");
                        }

                    } catch (Exception e) {
                        s_logger.error(e);
                    }
                }
            }).start();

        }
    }

};