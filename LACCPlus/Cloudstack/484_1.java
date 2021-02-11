//,temp,SolidFirePrimaryDataStoreDriver.java,797,825,temp,SolidFirePrimaryDataStoreDriver.java,525,567
//,3
public class xxx {
    @Override
    public void deleteAsync(DataStore dataStore, DataObject dataObject, AsyncCompletionCallback<CommandResult> callback) {
        String errMsg = null;

        try {
            if (dataObject.getType() == DataObjectType.VOLUME) {
                deleteVolume((VolumeInfo)dataObject, dataStore.getId());
            } else if (dataObject.getType() == DataObjectType.SNAPSHOT) {
                deleteSnapshot((SnapshotInfo)dataObject, dataStore.getId());
            } else if (dataObject.getType() == DataObjectType.TEMPLATE) {
                deleteTemplate((TemplateInfo)dataObject, dataStore.getId());
            } else {
                errMsg = "Invalid DataObjectType (" + dataObject.getType() + ") passed to deleteAsync";
            }
        }
        catch (Exception ex) {
            errMsg = ex.getMessage();

            LOGGER.error(errMsg);
        }

        if (callback != null) {
            CommandResult result = new CommandResult();

            result.setResult(errMsg);

            callback.complete(result);
        }
    }

};