//,temp,MockVmManagerImpl.java,501,511,temp,MockVmManagerImpl.java,481,488
//,3
public class xxx {
    @Override
    public Answer revertVmSnapshot(final RevertToVMSnapshotCommand cmd) {
        final String vm = cmd.getVmName();
        final String snapshot = cmd.getTarget().getSnapshotName();
        final MockVMVO vmVo = _mockVmDao.findByVmName(cmd.getVmName());
        if (vmVo == null) {
            return new RevertToVMSnapshotAnswer(cmd, false, "No VM by name " + cmd.getVmName());
        }
        s_logger.debug("Reverted to snapshot " + snapshot + " of VM " + vm);
        return new RevertToVMSnapshotAnswer(cmd, cmd.getVolumeTOs(), vmVo.getPowerState());
    }

};