//,temp,CloudStackPrimaryDataStoreDriverImpl.java,292,328,temp,BaseImageStoreDriverImpl.java,341,364
//,3
public class xxx {
    @Override
    public void takeSnapshot(SnapshotInfo snapshot, AsyncCompletionCallback<CreateCmdResult> callback) {
        CreateCmdResult result = null;
        try {
            SnapshotObjectTO snapshotTO = (SnapshotObjectTO) snapshot.getTO();
            Object payload = snapshot.getPayload();
            if (payload != null && payload instanceof CreateSnapshotPayload) {
                CreateSnapshotPayload snapshotPayload = (CreateSnapshotPayload) payload;
                snapshotTO.setQuiescevm(snapshotPayload.getQuiescevm());
            }

            CreateObjectCommand cmd = new CreateObjectCommand(snapshotTO);
            EndPoint ep = epSelector.select(snapshot, StorageAction.TAKESNAPSHOT);
            Answer answer = null;

            if (ep == null) {
                String errMsg = "No remote endpoint to send createObjectCommand, check if host or ssvm is down?";
                s_logger.error(errMsg);
                answer = new Answer(cmd, false, errMsg);
            } else {
                answer = ep.sendMessage(cmd);
            }

            result = new CreateCmdResult(null, answer);
            if (answer != null && !answer.getResult()) {
                result.setResult(answer.getDetails());
            }

            callback.complete(result);
            return;
        } catch (Exception e) {
            s_logger.debug("Failed to take snapshot: " + snapshot.getId(), e);
            result = new CreateCmdResult(null, null);
            result.setResult(e.toString());
        }
        callback.complete(result);
    }

};