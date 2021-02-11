//,temp,CreateVPCCmdByAdmin.java,39,68,temp,CreateVPCCmd.java,155,184
//,2
public class xxx {
    @Override
    public void execute() {
        Vpc vpc = null;
        try {
            if (isStart()) {
                _vpcService.startVpc(getEntityId(), true);
            } else {
                s_logger.debug("Not starting VPC as " + ApiConstants.START + "=false was passed to the API");
             }
            vpc = _entityMgr.findById(Vpc.class, getEntityId());
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        } catch (InsufficientCapacityException ex) {
            s_logger.info(ex);
            s_logger.trace(ex);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, ex.getMessage());
        }

        if (vpc != null) {
            VpcResponse response = _responseGenerator.createVpcResponse(ResponseView.Restricted, vpc);
            response.setResponseName(getCommandName());
            setResponseObject(response);
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create VPC");
        }
    }

};