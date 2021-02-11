//,temp,VirtualMachineManagerImpl.java,5256,5267,temp,VirtualMachineManagerImpl.java,5119,5129
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateStop(final VmWorkStop work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
            throw new CloudRuntimeException("Unable to find VM id=" + work.getVmId());
        }

        orchestrateStop(vm.getUuid(), work.isCleanup());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};