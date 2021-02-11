//,temp,CloudStackPrimaryDataStoreDriverImpl.java,212,239,temp,BaseImageStoreDriverImpl.java,341,364
//,3
public class xxx {
    @Override
    public void deleteAsync(DataStore dataStore, DataObject data, AsyncCompletionCallback<CommandResult> callback) {
        DeleteCommand cmd = new DeleteCommand(data.getTO());

        CommandResult result = new CommandResult();
        try {
            EndPoint ep = null;
            if (data.getType() == DataObjectType.VOLUME) {
                ep = epSelector.select(data, StorageAction.DELETEVOLUME);
            } else {
                ep = epSelector.select(data);
            }
            if (ep == null) {
                String errMsg = "No remote endpoint to send DeleteCommand, check if host or ssvm is down?";
                s_logger.error(errMsg);
                result.setResult(errMsg);
            } else {
                Answer answer = ep.sendMessage(cmd);
                if (answer != null && !answer.getResult()) {
                    result.setResult(answer.getDetails());
                }
            }
        } catch (Exception ex) {
            s_logger.debug("Unable to destoy volume" + data.getId(), ex);
            result.setResult(ex.toString());
        }
        callback.complete(result);
    }

};