//,temp,StoragePoolMonitor.java,165,185,temp,StoragePoolMonitor.java,143,163
//,3
public class xxx {
    @Override
    public void processHostAboutToBeRemoved(long hostId) {
        List<DataStoreProvider> providers = _dataStoreProviderMgr.getProviders();

        if (providers != null) {
            for (DataStoreProvider provider : providers) {
                if (provider instanceof PrimaryDataStoreProvider) {
                    try {
                        HypervisorHostListener hypervisorHostListener = provider.getHostListener();

                        if (hypervisorHostListener != null) {
                            hypervisorHostListener.hostAboutToBeRemoved(hostId);
                        }
                    }
                    catch (Exception ex) {
                        s_logger.error("hostAboutToBeRemoved(long) failed for storage provider " + provider.getName(), ex);
                    }
                }
            }
        }
    }

};