//,temp,Ovm3VirtualRoutingSupport.java,92,110,temp,HypervDirectConnectResource.java,2063,2079
//,3
public class xxx {
    private long[] getNetworkStats(String privateIP) {
        String result = networkUsage(privateIP, "get", null);
        long[] stats = new long[2];
        if (result != null) {
            try {
                String[] splitResult = result.split(":");
                int i = 0;
                while (i < splitResult.length - 1) {
                    stats[0] += (Long.parseLong(splitResult[i++]));
                    stats[1] += (Long.parseLong(splitResult[i++]));
                }
            } catch (Exception e) {
                LOGGER.warn(
                        "Unable to parse return from script return of network usage command: "
                                + e.toString(), e);
            }
        }
        return stats;
    }

};