//,temp,VirtualMachineManagerImpl.java,4065,4111,temp,VirtualMachineManagerImpl.java,2869,2912
//,3
public class xxx {
    @Override
    public VMInstanceVO reConfigureVm(final String vmUuid, final ServiceOffering oldServiceOffering,
            final boolean reconfiguringOnExistingHost)
                    throws ResourceUnavailableException, InsufficientServerCapacityException, ConcurrentOperationException {

        final AsyncJobExecutionContext jobContext = AsyncJobExecutionContext.getCurrentExecutionContext();
        if (jobContext.isJobDispatchedBy(VmWorkConstants.VM_WORK_JOB_DISPATCHER)) {
            // avoid re-entrance
            VmWorkJobVO placeHolder = null;
            final VirtualMachine vm = _vmDao.findByUuid(vmUuid);
            placeHolder = createPlaceHolderWork(vm.getId());
            try {
                return orchestrateReConfigureVm(vmUuid, oldServiceOffering, reconfiguringOnExistingHost);
            } finally {
                if (placeHolder != null) {
                    _workJobDao.expunge(placeHolder.getId());
                }
            }
        } else {
            final Outcome<VirtualMachine> outcome = reconfigureVmThroughJobQueue(vmUuid, oldServiceOffering, reconfiguringOnExistingHost);

            VirtualMachine vm = null;
            try {
                vm = outcome.get();
            } catch (final InterruptedException e) {
                throw new RuntimeException("Operation is interrupted", e);
            } catch (final java.util.concurrent.ExecutionException e) {
                throw new RuntimeException("Execution excetion", e);
            }

            final Object jobResult = _jobMgr.unmarshallResultObject(outcome.getJob());
            if (jobResult != null) {
                if (jobResult instanceof ResourceUnavailableException) {
                    throw (ResourceUnavailableException)jobResult;
                } else if (jobResult instanceof ConcurrentOperationException) {
                    throw (ConcurrentOperationException)jobResult;
                } else if (jobResult instanceof InsufficientServerCapacityException) {
                    throw (InsufficientServerCapacityException)jobResult;
                } else if (jobResult instanceof Throwable) {
                    s_logger.error("Unhandled exception", (Throwable)jobResult);
                    throw new RuntimeException("Unhandled exception", (Throwable)jobResult);
                }
            }

            return (VMInstanceVO)vm;
        }
    }

};