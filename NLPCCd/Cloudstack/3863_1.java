//,temp,sample_2107.java,2,8,temp,sample_2104.java,2,8
//,2
public class xxx {
public boolean startRemoteAccessVpn(final RemoteAccessVpn vpn, final VirtualRouter router) throws ResourceUnavailableException {
if (router.getState() != State.Running) {


log.info("unable to apply remote access vpn configuration virtual router is not in the right state");
}
}

};