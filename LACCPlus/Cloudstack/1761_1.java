//,temp,VirtualMachineManagerImpl.java,5215,5226,temp,VirtualMachineManagerImpl.java,5200,5213
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateRemoveNicFromVm(final VmWorkRemoveNicFromVm work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;
        final NicVO nic = _entityMgr.findById(NicVO.class, work.getNicId());
        final boolean result = orchestrateRemoveNicFromVm(vm, nic);
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED,
                _jobMgr.marshallResultObject(result));
    }

};