//,temp,RestartVPCCmd.java,91,112,temp,DeleteManagementNetworkIpRangeCmd.java,111,127
//,3
public class xxx {
    @Override
    public void execute() {
        try {
            _configService.deletePodIpRange(this);
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        } catch (Exception e) {
            s_logger.warn("Failed to delete management ip range from " + getStartIp() + " to " + getEndIp() + " of Pod: " + getPodId(), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

};