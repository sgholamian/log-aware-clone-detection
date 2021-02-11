//,temp,BaseImageStoreDriverImpl.java,341,364,temp,BaseImageStoreDriverImpl.java,273,295
//,3
public class xxx {
    @Override
    public void deleteAsync(DataStore dataStore, DataObject data, AsyncCompletionCallback<CommandResult> callback) {
        CommandResult result = new CommandResult();
        try {
            DeleteCommand cmd = new DeleteCommand(data.getTO());
            EndPoint ep = _epSelector.select(data);
            Answer answer = null;
            if (ep == null) {
                String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
                s_logger.error(errMsg);
                answer = new Answer(cmd, false, errMsg);
            } else {
                answer = ep.sendMessage(cmd);
            }
            if (answer != null && !answer.getResult()) {
                result.setResult(answer.getDetails());
            }
        } catch (Exception ex) {
            s_logger.debug("Unable to destoy " + data.getType().toString() + ": " + data.getId(), ex);
            result.setResult(ex.toString());
        }
        callback.complete(result);
    }

};