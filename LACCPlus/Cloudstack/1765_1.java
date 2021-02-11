//,temp,VirtualMachineManagerImpl.java,5241,5254,temp,VirtualMachineManagerImpl.java,5215,5226
//,3
public class xxx {
    @ReflectionUse
    private Pair<JobInfo.Status, String> orchestrateReconfigure(final VmWorkReconfigure work) throws Exception {
        final VMInstanceVO vm = _entityMgr.findById(VMInstanceVO.class, work.getVmId());
        if (vm == null) {
            s_logger.info("Unable to find vm " + work.getVmId());
        }
        assert vm != null;

        final ServiceOffering newServiceOffering = _offeringDao.findById(vm.getId(), work.getNewServiceOfferingId());

        reConfigureVm(vm.getUuid(), newServiceOffering,
                work.isSameHost());
        return new Pair<JobInfo.Status, String>(JobInfo.Status.SUCCEEDED, null);
    }

};