//,temp,MockVmManagerImpl.java,501,511,temp,MockVmManagerImpl.java,490,499
//,3
public class xxx {
    @Override
    public Answer deleteVmSnapshot(final DeleteVMSnapshotCommand cmd) {
        final String vm = cmd.getVmName();
        final String snapshotName = cmd.getTarget().getSnapshotName();
        if (_mockVmDao.findByVmName(cmd.getVmName()) == null) {
            return new DeleteVMSnapshotAnswer(cmd, false, "No VM by name " + cmd.getVmName());
        }
        s_logger.debug("Removed snapshot " + snapshotName + " of VM " + vm);
        return new DeleteVMSnapshotAnswer(cmd, cmd.getVolumeTOs());
    }

};