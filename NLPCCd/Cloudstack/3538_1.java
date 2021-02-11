//,temp,sample_2105.java,2,8,temp,sample_2104.java,2,8
//,2
public class xxx {
public boolean stopSite2SiteVpn(final Site2SiteVpnConnection conn, final VirtualRouter router) throws ResourceUnavailableException {
if (router.getState() != State.Running) {


log.info("unable to apply site to site vpn configuration virtual router is not in the right state");
}
}

};