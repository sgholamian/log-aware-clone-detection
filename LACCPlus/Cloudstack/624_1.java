//,temp,VMSnapshotManagerImpl.java,597,633,temp,VMSnapshotManagerImpl.java,533,595
//,3
public class xxx {
    private boolean orchestrateDeleteVMSnapshot(Long vmSnapshotId) {
        Account caller = getCaller();

        VMSnapshotVO vmSnapshot = _vmSnapshotDao.findById(vmSnapshotId);
        if (vmSnapshot == null) {
            throw new InvalidParameterValueException("unable to find the vm snapshot with id " + vmSnapshotId);
        }

        _accountMgr.checkAccess(caller, null, true, vmSnapshot);

        List<VMSnapshot.State> validStates = Arrays.asList(VMSnapshot.State.Ready, VMSnapshot.State.Expunging, VMSnapshot.State.Error, VMSnapshot.State.Allocated);
        // check VM snapshot states, only allow to delete vm snapshots in ready, expunging, allocated and error state
        if (!validStates.contains(vmSnapshot.getState())) {
            throw new InvalidParameterValueException("Can't delete the vm snapshot " + vmSnapshotId + " due to it is not in " + validStates.toString()  + "States");
        }

        // check if there are other active VM snapshot tasks
        if (hasActiveVMSnapshotTasks(vmSnapshot.getVmId())) {
            List<VMSnapshotVO> expungingSnapshots = _vmSnapshotDao.listByInstanceId(vmSnapshot.getVmId(), VMSnapshot.State.Expunging);
            if (expungingSnapshots.size() > 0 && expungingSnapshots.get(0).getId() == vmSnapshot.getId())
                s_logger.debug("Target VM snapshot already in expunging state, go on deleting it: " + vmSnapshot.getDisplayName());
            else
                throw new InvalidParameterValueException("There is other active vm snapshot tasks on the instance, please try again later");
        }

        if (vmSnapshot.getState() == VMSnapshot.State.Allocated) {
            return _vmSnapshotDao.remove(vmSnapshot.getId());
        } else {
            try {
                VMSnapshotStrategy strategy = findVMSnapshotStrategy(vmSnapshot);
                return strategy.deleteVMSnapshot(vmSnapshot);
            } catch (Exception e) {
                s_logger.debug("Failed to delete vm snapshot: " + vmSnapshotId, e);
                return false;
            }
        }
    }

};