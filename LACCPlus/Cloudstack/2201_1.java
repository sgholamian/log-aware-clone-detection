//,temp,GetVolumeiScsiNameCmd.java,55,67,temp,GetVolumeSnapshotDetailsCmd.java,59,72
//,3
public class xxx {
    @Override
    public void execute() {
        LOGGER.info("'GetVolumeiScsiNameCmd.execute' method invoked");

        String volume_iScsiName = _util.getVolume_iScsiName(volumeUuid);

        ApiVolumeiScsiNameResponse response = new ApiVolumeiScsiNameResponse(volume_iScsiName);

        response.setResponseName(getCommandName());
        response.setObjectName("apivolumeiscsiname");

        this.setResponseObject(response);
    }

};