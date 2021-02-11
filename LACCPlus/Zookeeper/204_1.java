//,temp,NettyServerCnxnFactory.java,529,545,temp,AuthFastLeaderElection.java,225,241
//,3
public class xxx {
    void removeCnxnFromIpMap(NettyServerCnxn cnxn, InetAddress remoteAddress) {
        synchronized (ipMap) {
            Set<NettyServerCnxn> s = ipMap.get(remoteAddress);
            if (s != null) {
                s.remove(cnxn);
                if (s.isEmpty()) {
                    ipMap.remove(remoteAddress);
                }
                return;
            }
        }
        // Fallthrough and log errors outside the synchronized block
        LOG.error(
                "Unexpected null set for remote address {} when removing cnxn {}",
                remoteAddress,
                cnxn);
    }

};