//,temp,DefaultVMSnapshotStrategy.java,353,411,temp,DefaultVMSnapshotStrategy.java,192,238
//,3
public class xxx {
    @Override
    public boolean deleteVMSnapshot(VMSnapshot vmSnapshot) {
        UserVmVO userVm = userVmDao.findById(vmSnapshot.getVmId());
        VMSnapshotVO vmSnapshotVO = (VMSnapshotVO)vmSnapshot;
        try {
            vmSnapshotHelper.vmSnapshotStateTransitTo(vmSnapshot, VMSnapshot.Event.ExpungeRequested);
        } catch (NoTransitionException e) {
            s_logger.debug("Failed to change vm snapshot state with event ExpungeRequested");
            throw new CloudRuntimeException("Failed to change vm snapshot state with event ExpungeRequested: " + e.getMessage());
        }

        try {
            Long hostId = vmSnapshotHelper.pickRunningHost(vmSnapshot.getVmId());

            List<VolumeObjectTO> volumeTOs = vmSnapshotHelper.getVolumeTOList(vmSnapshot.getVmId());

            String vmInstanceName = userVm.getInstanceName();
            VMSnapshotTO parent = vmSnapshotHelper.getSnapshotWithParents(vmSnapshotVO).getParent();
            VMSnapshotTO vmSnapshotTO =
                new VMSnapshotTO(vmSnapshot.getId(), vmSnapshot.getName(), vmSnapshot.getType(), vmSnapshot.getCreated().getTime(), vmSnapshot.getDescription(),
                    vmSnapshot.getCurrent(), parent, true);
            GuestOSVO guestOS = guestOSDao.findById(userVm.getGuestOSId());
            DeleteVMSnapshotCommand deleteSnapshotCommand = new DeleteVMSnapshotCommand(vmInstanceName, vmSnapshotTO, volumeTOs, guestOS.getDisplayName());

            Answer answer = agentMgr.send(hostId, deleteSnapshotCommand);

            if (answer != null && answer.getResult()) {
                DeleteVMSnapshotAnswer deleteVMSnapshotAnswer = (DeleteVMSnapshotAnswer)answer;
                processAnswer(vmSnapshotVO, userVm, answer, hostId);
                long full_chain_size=0;
                for (VolumeObjectTO volumeTo : deleteVMSnapshotAnswer.getVolumeTOs()) {
                    publishUsageEvent(EventTypes.EVENT_VM_SNAPSHOT_DELETE, vmSnapshot, userVm, volumeTo);
                    full_chain_size += volumeTo.getSize();
                }
                publishUsageEvent(EventTypes.EVENT_VM_SNAPSHOT_OFF_PRIMARY, vmSnapshot, userVm, full_chain_size, 0L);
                return true;
            } else {
                String errMsg = (answer == null) ? null : answer.getDetails();
                s_logger.error("Delete vm snapshot " + vmSnapshot.getName() + " of vm " + userVm.getInstanceName() + " failed due to " + errMsg);
                throw new CloudRuntimeException("Delete vm snapshot " + vmSnapshot.getName() + " of vm " + userVm.getInstanceName() + " failed due to " + errMsg);
            }
        } catch (OperationTimedoutException e) {
            throw new CloudRuntimeException("Delete vm snapshot " + vmSnapshot.getName() + " of vm " + userVm.getInstanceName() + " failed due to " + e.getMessage());
        } catch (AgentUnavailableException e) {
            throw new CloudRuntimeException("Delete vm snapshot " + vmSnapshot.getName() + " of vm " + userVm.getInstanceName() + " failed due to " + e.getMessage());
        }
    }

};