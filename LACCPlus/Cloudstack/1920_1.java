//,temp,LibvirtGetVmNetworkStatsCommandWrapper.java,42,67,temp,LibvirtGetVmStatsCommandWrapper.java,42,68
//,3
public class xxx {
    @Override
    public Answer execute(final GetVmNetworkStatsCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final List<String> vmNames = command.getVmNames();
        final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();

        try {
            final HashMap<String, List<VmNetworkStatsEntry>> vmNetworkStatsNameMap = new HashMap<String, List<VmNetworkStatsEntry>>();
            final Connect conn = libvirtUtilitiesHelper.getConnection();
            for (final String vmName : vmNames) {
                try {
                    final List<VmNetworkStatsEntry> statEntry = libvirtComputingResource.getVmNetworkStat(conn, vmName);
                    if (statEntry == null) {
                        continue;
                    }

                    vmNetworkStatsNameMap.put(vmName, statEntry);
                } catch (LibvirtException e) {
                    s_logger.warn("Can't get vm network stats: " + e.toString() + ", continue");
                }
            }
            return new GetVmNetworkStatsAnswer(command, "", command.getHostName(), vmNetworkStatsNameMap);
        } catch (final LibvirtException e) {
            s_logger.debug("Can't get vm network stats: " + e.toString());
            return new GetVmNetworkStatsAnswer(command, null, null, null);
        }
    }

};