//,temp,VirtualMachineManagerImpl.java,5215,5226,temp,VirtualMachineManagerImpl.java,5161,5173
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateMigrateWithStorage(final VmWorkMigrateWithStorage work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;
        orchestrateMigrateWithStorage(vm.getUuid(),
                work.getSrcHostId(),
                work.getDestHostId(),
                work.getVolumeToPool());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};