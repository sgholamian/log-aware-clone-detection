//,temp,GetVolumeiScsiNameCmd.java,55,67,temp,GetVolumeSnapshotDetailsCmd.java,59,72
//,3
public class xxx {
    @Override
    public void execute() {
        LOGGER.info("'" + GetVolumeSnapshotDetailsCmd.class.getSimpleName() + ".execute' method invoked");

        List<ApiVolumeSnapshotDetailsResponse> responses = util.getSnapshotDetails(snapshotUuid);

        ListResponse<ApiVolumeSnapshotDetailsResponse> listReponse = new ListResponse<>();

        listReponse.setResponses(responses);
        listReponse.setResponseName(getCommandName());
        listReponse.setObjectName("apivolumesnapshotdetails");

        this.setResponseObject(listReponse);
    }

};