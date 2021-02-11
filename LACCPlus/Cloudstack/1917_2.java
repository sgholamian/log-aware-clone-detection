//,temp,LibvirtGetVmDiskStatsCommandWrapper.java,42,67,temp,LibvirtGetVmStatsCommandWrapper.java,42,68
//,3
public class xxx {
    @Override
    public Answer execute(final GetVmStatsCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final List<String> vmNames = command.getVmNames();
        try {
            final HashMap<String, VmStatsEntry> vmStatsNameMap = new HashMap<String, VmStatsEntry>();
            for (final String vmName : vmNames) {

                final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();

                final Connect conn = libvirtUtilitiesHelper.getConnectionByVmName(vmName);
                try {
                    final VmStatsEntry statEntry = libvirtComputingResource.getVmStat(conn, vmName);
                    if (statEntry == null) {
                        continue;
                    }

                    vmStatsNameMap.put(vmName, statEntry);
                } catch (LibvirtException e) {
                    s_logger.warn("Can't get vm stats: " + e.toString() + ", continue");
                }
            }
            return new GetVmStatsAnswer(command, vmStatsNameMap);
        } catch (final LibvirtException e) {
            s_logger.debug("Can't get vm stats: " + e.toString());
            return new GetVmStatsAnswer(command, null);
        }
    }

};