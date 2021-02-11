//,temp,CreateLBStickinessPolicyCmd.java,160,170,temp,CreatePortableIpRangeCmd.java,124,138
//,3
public class xxx {
    @Override
    public void create() throws ResourceAllocationException {
        try {
            PortableIpRange portableIpRange = _configService.createPortableIpRange(this);
            if (portableIpRange != null) {
                this.setEntityId(portableIpRange.getId());
                this.setEntityUuid(portableIpRange.getUuid());
            } else {
                throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to create portable public IP range");
            }
        } catch (ConcurrentOperationException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }

};