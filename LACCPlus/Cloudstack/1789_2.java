//,temp,SecondaryStorageManagerImpl.java,1296,1309,temp,IpAddressManagerImpl.java,2004,2020
//,3
public class xxx {
    @Override
    public boolean handleSystemIpRelease(IpAddress ip) {
        boolean success = true;
        Long networkId = ip.getAssociatedWithNetworkId();
        if (networkId != null) {
            if (ip.getSystem()) {
                CallContext ctx = CallContext.current();
                if (!disassociatePublicIpAddress(ip.getId(), ctx.getCallingUserId(), ctx.getCallingAccount())) {
                    s_logger.warn("Unable to release system ip address id=" + ip.getId());
                    success = false;
                } else {
                    s_logger.warn("Successfully released system ip address id=" + ip.getId());
                }
            }
        }
        return success;
    }

};