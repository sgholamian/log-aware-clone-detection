//,temp,HypervDirectConnectResource.java,557,580,temp,HypervDirectConnectResource.java,526,554
//,3
public class xxx {
    private UnPlugNicAnswer execute(final UnPlugNicCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource UnPlugNicCommand " + s_gson.toJson(cmd));
        }

        try {
            final String vmName = cmd.getVmName();
            final NicTO nic = cmd.getNic();
            final URI broadcastUri = nic.getBroadcastUri();
            if (BroadcastDomainType.getSchemeValue(broadcastUri) != BroadcastDomainType.Vlan) {
                throw new InternalErrorException("Unable to unassign a public IP to a VIF on network " + nic.getBroadcastUri());
            }
            final String vlanId = BroadcastDomainType.getValue(broadcastUri);
            int publicNicInfo = -1;
            publicNicInfo = getVmNics(vmName, vlanId);
            if (publicNicInfo > 0) {
                modifyNicVlan(vmName, "2", publicNicInfo, false, "");
            }
            return new UnPlugNicAnswer(cmd, true, "success");
        } catch (final Exception e) {
            s_logger.error("Unexpected exception: ", e);
            return new UnPlugNicAnswer(cmd, false, "Unable to execute unPlugNicCommand due to " + e.toString());
        }
    }

};