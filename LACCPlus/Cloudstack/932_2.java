//,temp,TemplateManagerImpl.java,712,728,temp,CloudStackImageStoreDriverImpl.java,118,140
//,3
public class xxx {
    @Override
    public void deleteEntityExtractUrl(DataStore store, String installPath, String downloadUrl, Upload.Type entityType) {
        // find an endpoint to send command based on the ssvm on which the url was created.
        EndPoint ep = _epSelector.select(store, downloadUrl);

        // Delete Symlink at ssvm. In case of volume also delete the volume.
        DeleteEntityDownloadURLCommand cmd = new DeleteEntityDownloadURLCommand(installPath, entityType, downloadUrl, ((ImageStoreEntity) store).getMountPoint());

        Answer ans = null;
        if (ep == null) {
            String errMsg = "No remote endpoint to send command, check if host or ssvm is down?";
            s_logger.error(errMsg);
            ans = new Answer(cmd, false, errMsg);
        } else {
            ans = ep.sendMessage(cmd);
        }
        if (ans == null || !ans.getResult()) {
            String errorString = "Unable to delete the url " + downloadUrl + " for path " + installPath + " on ssvm, " + ans.getDetails();
            s_logger.error(errorString);
            throw new CloudRuntimeException(errorString);
        }

    }

};