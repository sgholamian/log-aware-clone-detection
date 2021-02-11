//,temp,VmwareResource.java,6026,6042,temp,HypervDirectConnectResource.java,2063,2079
//,2
public class xxx {
    private long[] getNetworkStats(final String privateIP) {
        final String result = networkUsage(privateIP, "get", null);
        final long[] stats = new long[2];
        if (result != null) {
            try {
                final String[] splitResult = result.split(":");
                int i = 0;
                while (i < splitResult.length - 1) {
                    stats[0] += Long.parseLong(splitResult[i++]);
                    stats[1] += Long.parseLong(splitResult[i++]);
                }
            } catch (final Throwable e) {
                s_logger.warn("Unable to parse return from script return of network usage command: " + e.toString(), e);
            }
        }
        return stats;
    }

};