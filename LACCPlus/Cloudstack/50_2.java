//,temp,VmwareResource.java,6120,6135,temp,HypervDirectConnectResource.java,2026,2043
//,3
public class xxx {
    private static String getRouterSshControlIp(final NetworkElementCommand cmd) {
        final String routerIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
        final String routerGuestIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_GUEST_IP);
        final String zoneNetworkType = cmd.getAccessDetail(NetworkElementCommand.ZONE_NETWORK_TYPE);

        if (routerGuestIp != null && zoneNetworkType != null && NetworkType.valueOf(zoneNetworkType) == NetworkType.Basic) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("In Basic zone mode, use router's guest IP for SSH control. guest IP : " + routerGuestIp);
            }

            return routerGuestIp;
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Use router's private IP for SSH control. IP : " + routerIp);
        }
        return routerIp;
    }

};