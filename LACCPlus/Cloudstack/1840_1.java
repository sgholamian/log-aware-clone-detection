//,temp,HypervDirectConnectResource.java,774,794,temp,HypervDirectConnectResource.java,753,772
//,3
public class xxx {
    private ExecutionResult prepareNetworkElementCommand(final SetNetworkACLCommand cmd) {
        final NicTO nic = cmd.getNic();
        final String routerName =
                cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);

        try {
            final URI broadcastUri = nic.getBroadcastUri();
            final String vlanId = BroadcastDomainType.getValue(broadcastUri);
            final int ethDeviceNum = getVmNics(routerName, vlanId);
            if (ethDeviceNum > 0) {
                nic.setDeviceId(ethDeviceNum);
            } else {
                return new ExecutionResult(false, "Prepare SetNetworkACL failed due to unable to find the nic");
            }
        } catch (final Exception e) {
            final String msg = "Prepare SetNetworkACL failed due to " + e.toString();
            s_logger.error(msg, e);
            return new ExecutionResult(false, msg);
        }
        return new ExecutionResult(true, null);
    }

};