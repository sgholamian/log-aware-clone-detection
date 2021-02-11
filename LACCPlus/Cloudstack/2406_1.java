//,temp,VirtualMachineManagerImpl.java,5131,5141,temp,VirtualMachineManagerImpl.java,5119,5129
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateMigrate(final VmWorkMigrate work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;

        orchestrateMigrate(vm.getUuid(), work.getSrcHostId(), work.getDeployDestination());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};