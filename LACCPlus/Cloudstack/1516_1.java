//,temp,VirtualMachineManagerImpl.java,5175,5187,temp,VirtualMachineManagerImpl.java,5161,5173
//,2
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateMigrateForScale(final VmWorkMigrateForScale work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;
        orchestrateMigrateForScale(vm.getUuid(),
                work.getSrcHostId(),
                work.getDeployDestination(),
                work.getNewServiceOfferringId());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};