//,temp,GetVolumeSnapshotDetailsCmd.java,59,72,temp,GetPathForVolumeCmd.java,54,66
//,3
public class xxx {
    @Override
    public void execute() {
        LOGGER.info("'GetPathForVolumeIdCmd.execute' method invoked");

        String pathForVolume = _util.getPathForVolumeUuid(_volumeUuid);

        ApiPathForVolumeResponse response = new ApiPathForVolumeResponse(pathForVolume);

        response.setResponseName(getCommandName());
        response.setObjectName("apipathforvolume");

        setResponseObject(response);
    }

};