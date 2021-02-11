//,temp,GetSolidFireVolumeSizeCmd.java,57,69,temp,UpdateSiocInfoCmd.java,85,104
//,3
public class xxx {
    @Override
    public void execute() {
        s_logger.info("'UpdateSiocInfoCmd.execute' method invoked");

        String msg = "Success";

        try {
            manager.updateSiocInfo(zoneId, storagePoolId, sharesPerGB, limitIopsPerGB, iopsNotifyThreshold);
        }
        catch (Exception ex) {
            msg = ex.getMessage();
        }

        ApiUpdateSiocInfoResponse response = new ApiUpdateSiocInfoResponse(msg);

        response.setResponseName(getCommandName());
        response.setObjectName("apiupdatesiocinfo");

        setResponseObject(response);
    }

};