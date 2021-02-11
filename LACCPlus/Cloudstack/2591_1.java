//,temp,BasicNetworkTopology.java,268,308,temp,AdvancedNetworkTopology.java,90,118
//,3
public class xxx {
    @Override
    public String[] applyVpnUsers(final Network network, final List<? extends VpnUser> users, final List<DomainRouterVO> routers) throws ResourceUnavailableException {
        if (routers == null || routers.isEmpty()) {
            s_logger.warn("Failed to add/remove VPN users: no router found for account and zone");
            throw new ResourceUnavailableException("Unable to assign ip addresses, domR doesn't exist for network " + network.getId(), DataCenter.class, network.getDataCenterId());
        }

        s_logger.debug("APPLYING BASIC VPN RULES");

        final BasicVpnRules vpnRules = new BasicVpnRules(network, users);
        boolean agentResults = true;

        for (final DomainRouterVO router : routers) {
            if(router.getState() == State.Stopped || router.getState() == State.Stopping){
                s_logger.info("The router " + router.getInstanceName()+ " is in the " + router.getState() + " state. So not applying the VPN rules. Will be applied once the router gets restarted.");
                continue;
            }
            else if (router.getState() != State.Running) {
                s_logger.warn("Failed to add/remove VPN users: router not in running state");
                throw new ResourceUnavailableException("Unable to assign ip addresses, domR is not in right state " + router.getState(), DataCenter.class,
                        network.getDataCenterId());
            }

            // Currently we receive just one answer from the agent. In the
            // future we have to parse individual answers and set
            // results accordingly
            final boolean agentResult = vpnRules.accept(_basicVisitor, router);
            agentResults = agentResults && agentResult;
        }

        final String[] result = new String[users.size()];
        for (int i = 0; i < result.length; i++) {
            if (agentResults) {
                result[i] = null;
            } else {
                result[i] = String.valueOf(agentResults);
            }
        }

        return result;
    }

};