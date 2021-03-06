//,temp,VirtualMachineManagerImpl.java,5228,5239,temp,VirtualMachineManagerImpl.java,5131,5141
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateRemoveVmFromNetwork(final VmWorkRemoveVmFromNetwork work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;
        final boolean result = orchestrateRemoveVmFromNetwork(vm,
                work.getNetwork(), work.getBroadcastUri());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED,
                _jobMgr.marshallResultObject(result));
    }

};