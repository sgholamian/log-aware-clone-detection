//,temp,VirtualMachineManagerImpl.java,5200,5213,temp,VirtualMachineManagerImpl.java,5119,5129
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateAddVmToNetwork(final VmWorkAddVmToNetwork work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;

        final Network network = _networkDao.findById(work.getNetworkId());
        final NicProfile nic = orchestrateAddVmToNetwork(vm, network,
                work.getRequestedNicProfile());

        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, _jobMgr.marshallResultObject(nic));
    }

};