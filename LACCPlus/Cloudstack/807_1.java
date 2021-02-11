//,temp,GetSolidFireVolumeSizeCmd.java,57,69,temp,GetVolumeiScsiNameCmd.java,55,67
//,3
public class xxx {
    @Override
    public void execute() {
        LOGGER.info("'GetSolidFireVolumeSizeCmd.execute' method invoked");

        long sfVolumeSize = manager.getSolidFireVolumeSize(volumeUuid);

        ApiSolidFireVolumeSizeResponse response = new ApiSolidFireVolumeSizeResponse(sfVolumeSize);

        response.setResponseName(getCommandName());
        response.setObjectName("apisolidfirevolumesize");

        this.setResponseObject(response);
    }

};