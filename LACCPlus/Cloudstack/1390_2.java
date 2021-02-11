//,temp,VirtualMachineManagerImpl.java,4065,4111,temp,VirtualMachineManagerImpl.java,2869,2912
//,3
public class xxx {
    @Override
    public void migrateAway(final String vmUuid, final long srcHostId) throws InsufficientServerCapacityException {
        final AsyncJobExecutionContext jobContext = AsyncJobExecutionContext.getCurrentExecutionContext();
        if (jobContext.isJobDispatchedBy(VmWorkConstants.VM_WORK_JOB_DISPATCHER)) {
            // avoid re-entrance

            VmWorkJobVO placeHolder = null;
            final VirtualMachine vm = _vmDao.findByUuid(vmUuid);
            placeHolder = createPlaceHolderWork(vm.getId());
            try {
                try {
                    orchestrateMigrateAway(vmUuid, srcHostId, null);
                } catch (final InsufficientServerCapacityException e) {
                    s_logger.warn("Failed to deploy vm " + vmUuid + " with original planner, sending HAPlanner");
                    orchestrateMigrateAway(vmUuid, srcHostId, _haMgr.getHAPlanner());
                }
            } finally {
                _workJobDao.expunge(placeHolder.getId());
            }
        } else {
            final Outcome<VirtualMachine> outcome = migrateVmAwayThroughJobQueue(vmUuid, srcHostId);

            try {
                final VirtualMachine vm = outcome.get();
            } catch (final InterruptedException e) {
                throw new RuntimeException("Operation is interrupted", e);
            } catch (final java.util.concurrent.ExecutionException e) {
                throw new RuntimeException("Execution excetion", e);
            }

            final Object jobException = _jobMgr.unmarshallResultObject(outcome.getJob());
            if (jobException != null) {
                if (jobException instanceof InsufficientServerCapacityException) {
                    throw (InsufficientServerCapacityException)jobException;
                } else if (jobException instanceof ConcurrentOperationException) {
                    throw (ConcurrentOperationException)jobException;
                } else if (jobException instanceof RuntimeException) {
                    throw (RuntimeException)jobException;
                } else if (jobException instanceof Throwable) {
                    throw new RuntimeException("Unexpected exception", (Throwable)jobException);
                }
            }
        }
    }

};