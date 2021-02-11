//,temp,VMSnapshotManagerImpl.java,597,633,temp,VMSnapshotManagerImpl.java,533,595
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_VM_SNAPSHOT_DELETE, eventDescription = "delete vm snapshots", async = true)
    public boolean deleteVMSnapshot(Long vmSnapshotId) {
        Account caller = getCaller();

        VMSnapshotVO vmSnapshot = _vmSnapshotDao.findById(vmSnapshotId);
        if (vmSnapshot == null) {
            throw new InvalidParameterValueException("unable to find the vm snapshot with id " + vmSnapshotId);
        }

        _accountMgr.checkAccess(caller, null, true, vmSnapshot);

        // check VM snapshot states, only allow to delete vm snapshots in created and error state
        if (VMSnapshot.State.Ready != vmSnapshot.getState() && VMSnapshot.State.Expunging != vmSnapshot.getState() && VMSnapshot.State.Error != vmSnapshot.getState()) {
            throw new InvalidParameterValueException("Can't delete the vm snapshotshot " + vmSnapshotId + " due to it is not in Created or Error, or Expunging State");
        }

        // check if there are other active VM snapshot tasks
        if (hasActiveVMSnapshotTasks(vmSnapshot.getVmId())) {
            List<VMSnapshotVO> expungingSnapshots = _vmSnapshotDao.listByInstanceId(vmSnapshot.getVmId(), VMSnapshot.State.Expunging);
            if (expungingSnapshots.size() > 0 && expungingSnapshots.get(0).getId() == vmSnapshot.getId())
                s_logger.debug("Target VM snapshot already in expunging state, go on deleting it: " + vmSnapshot.getDisplayName());
            else
                throw new InvalidParameterValueException("There is other active vm snapshot tasks on the instance, please try again later");
        }

        // serialize VM operation
        AsyncJobExecutionContext jobContext = AsyncJobExecutionContext.getCurrentExecutionContext();
        if (jobContext.isJobDispatchedBy(VmWorkConstants.VM_WORK_JOB_DISPATCHER)) {
            // avoid re-entrance
            VmWorkJobVO placeHolder = null;
            placeHolder = createPlaceHolderWork(vmSnapshot.getVmId());
            try {
                return orchestrateDeleteVMSnapshot(vmSnapshotId);
            } finally {
                _workJobDao.expunge(placeHolder.getId());
            }
        } else {
            Outcome<VMSnapshot> outcome = deleteVMSnapshotThroughJobQueue(vmSnapshot.getVmId(), vmSnapshotId);

            VMSnapshot result = null;
            try {
                result = outcome.get();
            } catch (InterruptedException e) {
                throw new RuntimeException("Operation is interrupted", e);
            } catch (java.util.concurrent.ExecutionException e) {
                throw new RuntimeException("Execution excetion", e);
            }

            Object jobResult = _jobMgr.unmarshallResultObject(outcome.getJob());
            if (jobResult != null) {
                if (jobResult instanceof ConcurrentOperationException)
                    throw (ConcurrentOperationException)jobResult;
                else if (jobResult instanceof Throwable)
                    throw new RuntimeException("Unexpected exception", (Throwable)jobResult);
            }

            if (jobResult instanceof Boolean)
                return ((Boolean)jobResult).booleanValue();

            return false;
        }
    }

};