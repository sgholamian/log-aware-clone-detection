//,temp,VmwareResource.java,1085,1099,temp,HypervDirectConnectResource.java,698,718
//,3
public class xxx {
    protected ExecutionResult prepareNetworkElementCommand(final SetupGuestNetworkCommand cmd) {
        final NicTO nic = cmd.getNic();
        final String domrName =
                cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);

        try {
            final URI broadcastUri = nic.getBroadcastUri();
            final String vlanId = BroadcastDomainType.getValue(broadcastUri);
            final int ethDeviceNum = getVmNics(domrName, vlanId);
            if (ethDeviceNum > 0) {
                nic.setDeviceId(ethDeviceNum);
            } else {
                return new ExecutionResult(false, "Prepare SetupGuestNetwork failed due to unable to find the nic");
            }
        } catch (final Exception e) {
            final String msg = "Prepare SetupGuestNetwork failed due to " + e.toString();
            s_logger.warn(msg, e);
            return new ExecutionResult(false, msg);
        }
        return new ExecutionResult(true, null);
    }

};