//,temp,RouterControlHelper.java,46,65,temp,InternalLoadBalancerVMManagerImpl.java,498,515
//,3
public class xxx {
    public String getRouterControlIp(final long routerId) {
        String routerControlIpAddress = null;
        final List<NicVO> nics = nicDao.listByVmId(routerId);
        for (final NicVO n : nics) {
            final NetworkVO nc = networkDao.findById(n.getNetworkId());
            if (nc != null && nc.getTrafficType() == TrafficType.Control) {
                routerControlIpAddress = n.getIPv4Address();
                // router will have only one control ip
                break;
            }
        }

        if (routerControlIpAddress == null) {
            logger.warn("Unable to find router's control ip in its attached NICs!. routerId: " + routerId);
            final DomainRouterVO router = routerDao.findById(routerId);
            return router.getPrivateIpAddress();
        }

        return routerControlIpAddress;
    }

};