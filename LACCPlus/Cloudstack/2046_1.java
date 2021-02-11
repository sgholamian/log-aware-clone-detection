//,temp,VmwareResource.java,964,1011,temp,HypervDirectConnectResource.java,1721,1763
//,3
public class xxx {
    private int findRouterEthDeviceIndex(String domrName, String routerIp, String mac) throws Exception {
        File keyFile = getSystemVmKeyFile();
        s_logger.info("findRouterEthDeviceIndex. mac: " + mac);
        ArrayList<String> skipInterfaces = new ArrayList<String>(Arrays.asList("all", "default", "lo"));

        // when we dynamically plug in a new NIC into virtual router, it may take time to show up in guest OS
        // we use a waiting loop here as a workaround to synchronize activities in systems
        long startTick = System.currentTimeMillis();
        long waitTimeoutMillis = VmwareManager.s_vmwareNicHotplugWaitTimeout.value();
        while (System.currentTimeMillis() - startTick < waitTimeoutMillis) {

            // TODO : this is a temporary very inefficient solution, will refactor it later
            Pair<Boolean, String> result = SshHelper.sshExecute(routerIp, DefaultDomRSshPort, "root", keyFile, null, "ls /proc/sys/net/ipv4/conf");
            if (result.first()) {
                String[] tokens = result.second().split("\\s+");
                for (String token : tokens) {
                    if (!(skipInterfaces.contains(token))) {
                        String cmd = String.format("ip address show %s | grep link/ether | sed -e 's/^[ \t]*//' | cut -d' ' -f2", token);

                        if (s_logger.isDebugEnabled())
                            s_logger.debug("Run domr script " + cmd);
                        Pair<Boolean, String> result2 = SshHelper.sshExecute(routerIp, DefaultDomRSshPort, "root", keyFile, null,
                                // TODO need to find the dev index inside router based on IP address
                                cmd);
                        if (s_logger.isDebugEnabled())
                            s_logger.debug("result: " + result2.first() + ", output: " + result2.second());

                        if (result2.first() && result2.second().trim().equalsIgnoreCase(mac.trim())) {
                            return Integer.parseInt(token.substring(3));
                        } else {
                            skipInterfaces.add(token);
                        }
                    }
                }
            }

            s_logger.warn("can not find intereface associated with mac: " + mac + ", guest OS may still at loading state, retry...");

            try {
                Thread.currentThread();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                s_logger.debug("[ignored] interupted while trying to get mac.");
            }
        }

        return -1;
    }

};