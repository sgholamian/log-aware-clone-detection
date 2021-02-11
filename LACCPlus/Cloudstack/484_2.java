//,temp,SolidFirePrimaryDataStoreDriver.java,797,825,temp,SolidFirePrimaryDataStoreDriver.java,525,567
//,3
public class xxx {
    @Override
    public void createAsync(DataStore dataStore, DataObject dataObject, AsyncCompletionCallback<CreateCmdResult> callback) {
        String iqn = null;
        String errMsg = null;

        try {
            if (dataObject.getType() == DataObjectType.VOLUME) {
                iqn = createVolume((VolumeInfo)dataObject, dataStore.getId());
            } else if (dataObject.getType() == DataObjectType.SNAPSHOT) {
                createTempVolume((SnapshotInfo)dataObject, dataStore.getId());
            } else if (dataObject.getType() == DataObjectType.TEMPLATE) {
                iqn = createTemplateVolume((TemplateInfo)dataObject, dataStore.getId());
            } else {
                errMsg = "Invalid DataObjectType (" + dataObject.getType() + ") passed to createAsync";

                LOGGER.error(errMsg);
            }
        }
        catch (Exception ex) {
            errMsg = ex.getMessage();

            LOGGER.error(errMsg);

            if (callback == null) {
                throw ex;
            }
        }

        if (callback != null) {
            // path = iqn
            // size is pulled from DataObject instance, if errMsg is null
            CreateCmdResult result = new CreateCmdResult(iqn, new Answer(null, errMsg == null, errMsg));

            result.setResult(errMsg);

            callback.complete(result);
        }
        else {
            if (errMsg != null) {
                throw new CloudRuntimeException(errMsg);
            }
        }
    }

};