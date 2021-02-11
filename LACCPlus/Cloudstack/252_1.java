//,temp,StoragePoolMonitor.java,165,185,temp,StoragePoolMonitor.java,143,163
//,3
public class xxx {
    @Override
    public void processHostRemoved(long hostId, long clusterId) {
        List<DataStoreProvider> providers = _dataStoreProviderMgr.getProviders();

        if (providers != null) {
            for (DataStoreProvider provider : providers) {
                if (provider instanceof PrimaryDataStoreProvider) {
                    try {
                        HypervisorHostListener hypervisorHostListener = provider.getHostListener();

                        if (hypervisorHostListener != null) {
                            hypervisorHostListener.hostRemoved(hostId, clusterId);
                        }
                    }
                    catch (Exception ex) {
                        s_logger.error("hostRemoved(long, long) failed for storage provider " + provider.getName(), ex);
                    }
                }
            }
        }
    }

};