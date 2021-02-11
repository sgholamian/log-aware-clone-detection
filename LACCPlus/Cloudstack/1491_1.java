//,temp,VpcVirtualNetworkApplianceManagerImpl.java,620,629,temp,VpcVirtualNetworkApplianceManagerImpl.java,609,618
//,2
public class xxx {
    @Override
    public boolean stopSite2SiteVpn(final Site2SiteVpnConnection conn, final VirtualRouter router) throws ResourceUnavailableException {
        if (router.getState() != State.Running) {
            s_logger.warn("Unable to apply site-to-site VPN configuration, virtual router is not in the right state " + router.getState());
            throw new ResourceUnavailableException("Unable to apply site 2 site VPN configuration," + " virtual router is not in the right state", DataCenter.class,
                    router.getDataCenterId());
        }

        return applySite2SiteVpn(false, router, conn);
    }

};