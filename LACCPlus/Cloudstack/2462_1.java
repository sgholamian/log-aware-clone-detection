//,temp,GetUploadParamsForVolumeCmd.java,57,68,temp,ListBaremetalDhcpCmd.java,83,98
//,3
public class xxx {
    @Override
    public void execute() throws ServerApiException {

        try {
            GetUploadParamsResponse response = _volumeService.uploadVolume(this);
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } catch (MalformedURLException | ResourceAllocationException e) {
            s_logger.error("exception while uploading volume", e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "exception while uploading a volume: " + e.getMessage());
        }
    }

};