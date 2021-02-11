//,temp,VirtualMachineManagerImpl.java,5215,5226,temp,VirtualMachineManagerImpl.java,5189,5198
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateReboot(final VmWorkReboot work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;
        orchestrateReboot(vm.getUuid(), work.getParams());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};