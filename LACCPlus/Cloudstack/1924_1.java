//,temp,CloudStackPrimaryDataStoreDriverImpl.java,330,353,temp,BaseImageStoreDriverImpl.java,341,364
//,3
public class xxx {
    @Override
    public void revertSnapshot(SnapshotInfo snapshot, SnapshotInfo snapshotOnPrimaryStore, AsyncCompletionCallback<CommandResult> callback) {
        SnapshotObjectTO snapshotTO = (SnapshotObjectTO)snapshot.getTO();
        RevertSnapshotCommand cmd = new RevertSnapshotCommand(snapshotTO);

        CommandResult result = new CommandResult();
        try {
            EndPoint ep = epSelector.select(snapshotOnPrimaryStore);
            if ( ep == null ){
                String errMsg = "No remote endpoint to send RevertSnapshotCommand, check if host or ssvm is down?";
                s_logger.error(errMsg);
                result.setResult(errMsg);
            } else {
                Answer answer = ep.sendMessage(cmd);
                if (answer != null && !answer.getResult()) {
                    result.setResult(answer.getDetails());
                }
            }
        } catch (Exception ex) {
            s_logger.debug("Unable to revert snapshot " + snapshot.getId(), ex);
            result.setResult(ex.toString());
        }
        callback.complete(result);
    }

};