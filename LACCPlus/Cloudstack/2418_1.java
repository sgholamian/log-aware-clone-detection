//,temp,VirtualMachineManagerImpl.java,5256,5267,temp,VirtualMachineManagerImpl.java,5189,5198
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateStorageMigration(final VmWorkStorageMigration work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;
        final StoragePool pool = (PrimaryDataStoreInfo)dataStoreMgr.getPrimaryDataStore(work.getDestStoragePoolId());
        orchestrateStorageMigration(vm.getUuid(), pool);

        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};