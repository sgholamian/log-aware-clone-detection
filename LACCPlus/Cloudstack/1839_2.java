//,temp,HypervDirectConnectResource.java,557,580,temp,HypervDirectConnectResource.java,526,554
//,3
public class xxx {
    private PlugNicAnswer execute(final PlugNicCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource PlugNicCommand " + s_gson.toJson(cmd));
        }

        try {

            final String vmName = cmd.getVmName();
            final NicTO nic = cmd.getNic();
            final URI broadcastUri = nic.getBroadcastUri();
            if (BroadcastDomainType.getSchemeValue(broadcastUri) != BroadcastDomainType.Vlan) {
                throw new InternalErrorException("Unable to assign a public IP to a VIF on network " + nic.getBroadcastUri());
            }
            final String vlanId = BroadcastDomainType.getValue(broadcastUri);
            int publicNicInfo = -1;
            publicNicInfo = getVmFreeNicIndex(vmName);
            if (publicNicInfo > 0) {
                modifyNicVlan(vmName, vlanId, publicNicInfo, true, cmd.getNic().getName());
                return new PlugNicAnswer(cmd, true, "success");
            }
            final String msg = " Plug Nic failed for the vm as it has reached max limit of NICs to be added";
            s_logger.warn(msg);
            return new PlugNicAnswer(cmd, false, msg);

        } catch (final Exception e) {
            s_logger.error("Unexpected exception: ", e);
            return new PlugNicAnswer(cmd, false, "Unable to execute PlugNicCommand due to " + e.toString());
        }
    }

};