//,temp,LibvirtGetVmNetworkStatsCommandWrapper.java,42,67,temp,LibvirtGetVmDiskStatsCommandWrapper.java,42,67
//,2
public class xxx {
    @Override
    public Answer execute(final GetVmDiskStatsCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final List<String> vmNames = command.getVmNames();
        final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();

        try {
            final HashMap<String, List<VmDiskStatsEntry>> vmDiskStatsNameMap = new HashMap<String, List<VmDiskStatsEntry>>();
            final Connect conn = libvirtUtilitiesHelper.getConnection();
            for (final String vmName : vmNames) {
                try {
                    final List<VmDiskStatsEntry> statEntry = libvirtComputingResource.getVmDiskStat(conn, vmName);
                    if (statEntry == null) {
                        continue;
                    }

                    vmDiskStatsNameMap.put(vmName, statEntry);
                } catch (LibvirtException e) {
                    s_logger.warn("Can't get vm disk stats: " + e.toString() + ", continue");
                }
            }
            return new GetVmDiskStatsAnswer(command, "", command.getHostName(), vmDiskStatsNameMap);
        } catch (final LibvirtException e) {
            s_logger.debug("Can't get vm disk stats: " + e.toString());
            return new GetVmDiskStatsAnswer(command, null, null, null);
        }
    }

};