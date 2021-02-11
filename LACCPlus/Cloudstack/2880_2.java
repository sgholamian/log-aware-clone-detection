//,temp,ResizeVolumeCmdByAdmin.java,36,55,temp,ResizeVolumeCmd.java,169,188
//,2
public class xxx {
    @Override
    public void execute() throws ResourceAllocationException {
        Volume volume = null;
        try {
            CallContext.current().setEventDetails("Volume Id: " + this._uuidMgr.getUuid(Volume.class, getEntityId()) + " to size " + getSize() + "G");
            volume = _volumeService.resizeVolume(this);
        } catch (InvalidParameterValueException ex) {
            s_logger.info(ex.getMessage());
            throw new ServerApiException(ApiErrorCode.UNSUPPORTED_ACTION_ERROR, ex.getMessage());
        }

        if (volume != null) {
            VolumeResponse response = _responseGenerator.createVolumeResponse(ResponseView.Restricted, volume);
            //FIXME - have to be moved to ApiResponseHelper
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to resize volume");
        }
    }

};