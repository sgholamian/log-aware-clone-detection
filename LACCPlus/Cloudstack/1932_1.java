//,temp,AssociateIPAddrCmd.java,302,327,temp,CreatePrivateGatewayCmd.java,146,168
//,3
public class xxx {
    @Override
    public void create() throws ResourceAllocationException {
        try {
            IpAddress ip = null;

            if (!isPortable()) {
                ip = _networkService.allocateIP(_accountService.getAccount(getEntityOwnerId()), getZoneId(), getNetworkId(), getDisplayIp());
            } else {
                ip = _networkService.allocatePortableIP(_accountService.getAccount(getEntityOwnerId()), 1, getZoneId(), getNetworkId(), getVpcId());
            }

            if (ip != null) {
                setEntityId(ip.getId());
                setEntityUuid(ip.getUuid());
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to allocate IP address");
            }
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        } catch (InsufficientAddressCapacityException ex) {
            s_logger.info(ex);
            s_logger.trace(ex);
            throw new ServerApiException(ApiErrorCode.INSUFFICIENT_CAPACITY_ERROR, ex.getMessage());
        }
    }

};