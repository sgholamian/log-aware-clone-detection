//,temp,HypervDirectConnectResource.java,1721,1763,temp,HypervDirectConnectResource.java,1679,1719
//,3
public class xxx {
    private int findRouterEthDeviceIndex(final String domrName, final String routerIp, final String mac) throws Exception {

        s_logger.info("findRouterEthDeviceIndex. mac: " + mac);

        // TODO : this is a temporary very inefficient solution, will refactor it later
        final Pair<Boolean, String> result = SshHelper.sshExecute(routerIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null,
                "ls /proc/sys/net/ipv4/conf");

        // when we dynamically plug in a new NIC into virtual router, it may take time to show up in guest OS
        // we use a waiting loop here as a workaround to synchronize activities in systems
        final long startTick = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTick < 15000) {
            if (result.first()) {
                final String[] tokens = result.second().split("\\s+");
                for (final String token : tokens) {
                    if (!("all".equalsIgnoreCase(token) || "default".equalsIgnoreCase(token) || "lo".equalsIgnoreCase(token))) {
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

                        if (result2.first() && result2.second().trim().equalsIgnoreCase(mac.trim())) {
                            return Integer.parseInt(token.substring(3));
                        }
                    }
                }
            }

            s_logger.warn("can not find intereface associated with mac: " + mac + ", guest OS may still at loading state, retry...");

        }

        return -1;
    }

};