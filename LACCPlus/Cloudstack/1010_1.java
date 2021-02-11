//,temp,EnableStaticNatCmd.java,130,145,temp,CreateVpnConnectionCmd.java,133,148
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException {
        try {
            boolean result = _rulesService.enableStaticNat(ipAddressId, virtualMachineId, getNetworkId(), getVmSecondaryIp());
            if (result) {
                SuccessResponse response = new SuccessResponse(getCommandName());
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to enable static NAT");
            }
        } catch (NetworkRuleConflictException ex) {
            s_logger.info("Network rule conflict: " + ex.getMessage());
            s_logger.trace("Network Rule Conflict: ", ex);
            throw new ServerApiException(ApiErrorCode.NETWORK_RULE_CONFLICT_ERROR, ex.getMessage());
        }
    }

};