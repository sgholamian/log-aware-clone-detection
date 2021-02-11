//,temp,MockVmManagerImpl.java,501,511,temp,MockVmManagerImpl.java,481,488
//,3
public class xxx {
    @Override
    public Answer createVmSnapshot(final CreateVMSnapshotCommand cmd) {
        final String vmName = cmd.getVmName();
        final String vmSnapshotName = cmd.getTarget().getSnapshotName();

        s_logger.debug("Created snapshot " + vmSnapshotName + " for vm " + vmName);
        return new CreateVMSnapshotAnswer(cmd, cmd.getTarget(), cmd.getVolumeTOs());
    }

};