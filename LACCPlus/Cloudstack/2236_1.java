//,temp,GetSolidFireVolumeAccessGroupIdsCmd.java,68,80,temp,GetPathForVolumeCmd.java,54,66
//,3
public class xxx {
    @Override
    public void execute() {
        LOGGER.info("'GetSolidFireVolumeAccessGroupIdsCmd.execute' method invoked");

        long[] sfVagIds = manager.getSolidFireVolumeAccessGroupIds(clusterUuid, storagePoolUuid);

        ApiSolidFireVolumeAccessGroupIdsResponse response = new ApiSolidFireVolumeAccessGroupIdsResponse(sfVagIds);

        response.setResponseName(getCommandName());
        response.setObjectName("apisolidfirevolumeaccessgroupids");

        this.setResponseObject(response);
    }

};