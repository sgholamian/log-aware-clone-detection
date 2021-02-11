//,temp,XenServer56NetworkUsageCommandWrapper.java,38,57,temp,XcpServerNetworkUsageCommandWrapper.java,37,53
//,3
public class xxx {
    @Override
    public Answer execute(final NetworkUsageCommand command, final XcpServerResource xcpServerResource) {
        try {
            final Connection conn = xcpServerResource.getConnection();
            if (command.getOption() != null && command.getOption().equals("create")) {
                final String result = xcpServerResource.networkUsage(conn, command.getPrivateIP(), "create", null);
                final NetworkUsageAnswer answer = new NetworkUsageAnswer(command, result, 0L, 0L);
                return answer;
            }
            final long[] stats = xcpServerResource.getNetworkStats(conn, command.getPrivateIP());
            final NetworkUsageAnswer answer = new NetworkUsageAnswer(command, "", stats[0], stats[1]);
            return answer;
        } catch (final Exception ex) {
            s_logger.warn("Failed to get network usage stats due to ", ex);
            return new NetworkUsageAnswer(command, ex);
        }
    }

};