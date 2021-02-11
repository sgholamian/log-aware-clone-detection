//,temp,GetSolidFireAccountIdCmd.java,59,71,temp,DeleteUcsManagerCmd.java,54,67
//,3
public class xxx {
    @Override
    public void execute() {
        LOGGER.info("'GetSolidFireAccountIdCmd.execute' method invoked");

        long sfAccountId = manager.getSolidFireAccountId(csAccountUuid, storagePoolUuid);

        ApiSolidFireAccountIdResponse response = new ApiSolidFireAccountIdResponse(sfAccountId);

        response.setResponseName(getCommandName());
        response.setObjectName("apisolidfireaccountid");

        this.setResponseObject(response);
    }

};