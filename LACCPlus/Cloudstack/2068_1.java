//,temp,DefaultVMSnapshotStrategy.java,353,411,temp,DefaultVMSnapshotStrategy.java,104,190
//,3
public class xxx {
    @Override
    public boolean revertVMSnapshot(VMSnapshot vmSnapshot) {
        VMSnapshotVO vmSnapshotVO = (VMSnapshotVO)vmSnapshot;
        UserVmVO userVm = userVmDao.findById(vmSnapshot.getVmId());
        try {
            vmSnapshotHelper.vmSnapshotStateTransitTo(vmSnapshotVO, VMSnapshot.Event.RevertRequested);
        } catch (NoTransitionException e) {
            throw new CloudRuntimeException(e.getMessage());
        }

        boolean result = false;
        try {
            VMSnapshotVO snapshot = vmSnapshotDao.findById(vmSnapshotVO.getId());
            List<VolumeObjectTO> volumeTOs = vmSnapshotHelper.getVolumeTOList(userVm.getId());
            String vmInstanceName = userVm.getInstanceName();
            VMSnapshotTO parent = vmSnapshotHelper.getSnapshotWithParents(snapshot).getParent();

            VMSnapshotTO vmSnapshotTO =
                new VMSnapshotTO(snapshot.getId(), snapshot.getName(), snapshot.getType(), snapshot.getCreated().getTime(), snapshot.getDescription(),
                    snapshot.getCurrent(), parent, true);
            Long hostId = vmSnapshotHelper.pickRunningHost(vmSnapshot.getVmId());
            GuestOSVO guestOS = guestOSDao.findById(userVm.getGuestOSId());
            RevertToVMSnapshotCommand revertToSnapshotCommand = new RevertToVMSnapshotCommand(vmInstanceName, userVm.getUuid(), vmSnapshotTO, volumeTOs, guestOS.getDisplayName());
            HostVO host = hostDao.findById(hostId);
            GuestOSHypervisorVO guestOsMapping = guestOsHypervisorDao.findByOsIdAndHypervisor(guestOS.getId(), host.getHypervisorType().toString(), host.getHypervisorVersion());
            if (guestOsMapping == null) {
                revertToSnapshotCommand.setPlatformEmulator(null);
            } else {
                revertToSnapshotCommand.setPlatformEmulator(guestOsMapping.getGuestOsName());
            }

            RevertToVMSnapshotAnswer answer = (RevertToVMSnapshotAnswer)agentMgr.send(hostId, revertToSnapshotCommand);
            if (answer != null && answer.getResult()) {
                processAnswer(vmSnapshotVO, userVm, answer, hostId);
                result = true;
            } else {
                String errMsg = "Revert VM: " + userVm.getInstanceName() + " to snapshot: " + vmSnapshotVO.getName() + " failed";
                if (answer != null && answer.getDetails() != null)
                    errMsg = errMsg + " due to " + answer.getDetails();
                s_logger.error(errMsg);
                throw new CloudRuntimeException(errMsg);
            }
        } catch (OperationTimedoutException e) {
            s_logger.debug("Failed to revert vm snapshot", e);
            throw new CloudRuntimeException(e.getMessage());
        } catch (AgentUnavailableException e) {
            s_logger.debug("Failed to revert vm snapshot", e);
            throw new CloudRuntimeException(e.getMessage());
        } finally {
            if (!result) {
                try {
                    vmSnapshotHelper.vmSnapshotStateTransitTo(vmSnapshot, VMSnapshot.Event.OperationFailed);
                } catch (NoTransitionException e1) {
                    s_logger.error("Cannot set vm snapshot state due to: " + e1.getMessage());
                }
            }
        }
        return result;
    }

};