//,temp,DeleteSecurityGroupCmd.java,122,136,temp,AddBaremetalRctCmd.java,66,75
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            boolean result = _securityGroupService.deleteSecurityGroup(this);
            if (result) {
                SuccessResponse response = new SuccessResponse(getCommandName());
                setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to delete security group");
            }
        } catch (ResourceInUseException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_IN_USE_ERROR, ex.getMessage());
        }
    }

};