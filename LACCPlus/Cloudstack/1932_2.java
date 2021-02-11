//,temp,AssociateIPAddrCmd.java,302,327,temp,CreatePrivateGatewayCmd.java,146,168
//,3
public class xxx {
    @Override
    public void create() throws ResourceAllocationException {
        PrivateGateway result = null;
        try {
            result =
                _vpcService.createVpcPrivateGateway(getVpcId(), getPhysicalNetworkId(), getBroadcastUri(), getStartIp(), getGateway(), getNetmask(), getEntityOwnerId(),
                    getNetworkOfferingId(), getIsSourceNat(), getAclId());
        } catch (InsufficientCapacityException ex) {
            s_logger.info(ex);
            s_logger.trace(ex);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, ex.getMessage());
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }

        if (result != null) {
            setEntityId(result.getId());
            setEntityUuid(result.getUuid());
        } else {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create private gateway");
        }
    }

};