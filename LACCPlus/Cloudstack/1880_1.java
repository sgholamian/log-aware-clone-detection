//,temp,XenServer56NetworkUsageCommandWrapper.java,38,57,temp,XcpServerNetworkUsageCommandWrapper.java,37,53
//,3
public class xxx {
    @Override
    public Answer execute(final NetworkUsageCommand command, final XenServer56Resource xenServer56) {
        if (command.isForVpc()) {
            return executeNetworkUsage(command, xenServer56);
        }
        try {
            final Connection conn = xenServer56.getConnection();
            if (command.getOption() != null && command.getOption().equals("create")) {
                final String result = xenServer56.networkUsage(conn, command.getPrivateIP(), "create", null);
                final NetworkUsageAnswer answer = new NetworkUsageAnswer(command, result, 0L, 0L);
                return answer;
            }
            final long[] stats = xenServer56.getNetworkStats(conn, command.getPrivateIP());
            final NetworkUsageAnswer answer = new NetworkUsageAnswer(command, "", stats[0], stats[1]);
            return answer;
        } catch (final Exception ex) {
            s_logger.warn("Failed to get network usage stats due to ", ex);
            return new NetworkUsageAnswer(command, ex);
        }
    }

};