//,temp,VmwareResource.java,964,1011,temp,HypervDirectConnectResource.java,1721,1763
//,3
public class xxx {
    private Pair<Integer, String> findRouterFreeEthDeviceIndex(final String routerIp) throws Exception {

        s_logger.info("findRouterFreeEthDeviceIndex. mac: ");

        // TODO : this is a temporary very inefficient solution, will refactor it later
        final Pair<Boolean, String> result = SshHelper.sshExecute(routerIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null,
                "ip address | grep DOWN| cut -f2 -d :");

        // when we dynamically plug in a new NIC into virtual router, it may take time to show up in guest OS
        // we use a waiting loop here as a workaround to synchronize activities in systems
        final long startTick = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTick < 15000) {
            if (result.first() && !result.second().isEmpty()) {
                final String[] tokens = result.second().split("\\n");
                for (final String token : tokens) {
                    if (!("all".equalsIgnoreCase(token) || "default".equalsIgnoreCase(token) || "lo".equalsIgnoreCase(token))) {
                        //String cmd = String.format("ip address show %s | grep link/ether | sed -e 's/^[ \t]*//' | cut -d' ' -f2", token);
                        //TODO: don't check for eth0,1,2, as they will be empty by default.
                        //String cmd = String.format("ip address show %s ", token);
                        final String cmd = String.format("ip address show %s | grep link/ether | sed -e 's/^[ \t]*//' | cut -d' ' -f2", token);
                        if (s_logger.isDebugEnabled()) {
                            s_logger.debug("Run domr script " + cmd);
                        }
                        final Pair<Boolean, String> result2 = SshHelper.sshExecute(routerIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null,
                                // TODO need to find the dev index inside router based on IP address
                                cmd);
                        if (s_logger.isDebugEnabled()) {
                            s_logger.debug("result: " + result2.first() + ", output: " + result2.second());
                        }

                        if (result2.first() && result2.second().trim().length() > 0) {
                            return new Pair<Integer, String>(Integer.parseInt(token.trim().substring(3)), result2.second().trim()) ;
                        }
                    }
                }
            }

            //s_logger.warn("can not find intereface associated with mac: , guest OS may still at loading state, retry...");

        }

        return new Pair<Integer, String>(-1, "");
    }

};