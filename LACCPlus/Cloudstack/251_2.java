//,temp,StoragePoolMonitor.java,165,185,temp,StoragePoolMonitor.java,73,93
//,3
public class xxx {
    @Override
    public void processHostAdded(long hostId) {
        List<DataStoreProvider> providers = _dataStoreProviderMgr.getProviders();

        if (providers != null) {
            for (DataStoreProvider provider : providers) {
                if (provider instanceof PrimaryDataStoreProvider) {
                    try {
                        HypervisorHostListener hypervisorHostListener = provider.getHostListener();

                        if (hypervisorHostListener != null) {
                            hypervisorHostListener.hostAdded(hostId);
                        }
                    }
                    catch (Exception ex) {
                        s_logger.error("hostAdded(long) failed for storage provider " + provider.getName(), ex);
                    }
                }
            }
        }
    }

};