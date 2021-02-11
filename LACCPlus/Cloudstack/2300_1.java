//,temp,CreateVlanIpRangeCmd.java,223,241,temp,CreateSecondaryStagingStoreCmd.java,108,125
//,3
public class xxx {
    @Override
    public void execute() throws ResourceUnavailableException, ResourceAllocationException {
        try {
            Vlan result = _configService.createVlanAndPublicIpRange(this);
            if (result != null) {
                VlanIpRangeResponse response = _responseGenerator.createVlanIpRangeResponse(result);
                response.setResponseName(getCommandName());
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create vlan ip range");
            }
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        } catch (InsufficientCapacityException ex) {
            s_logger.info(ex);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, ex.getMessage());
        }
    }

};