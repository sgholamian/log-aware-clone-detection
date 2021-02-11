//,temp,VmwareResource.java,1069,1083,temp,HypervDirectConnectResource.java,753,772
//,3
public class xxx {
    protected ExecutionResult prepareNetworkElementCommand(final SetSourceNatCommand cmd) {
        final String routerName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        final IpAddressTO pubIp = cmd.getIpAddress();

        try {
            final String broadcastUri = pubIp.getBroadcastUri();
            final String vlanId = BroadcastDomainType.getValue(broadcastUri);
            final int ethDeviceNum = getVmNics(routerName, vlanId);
            if (ethDeviceNum > 0) {
                pubIp.setNicDevId(ethDeviceNum);
            } else {
                return new ExecutionResult(false, "Prepare Ip SNAT failed due to unable to find the nic");
            }
        } catch (final Exception e) {
            final String msg = "Prepare Ip SNAT failure due to " + e.toString();
            s_logger.error(msg, e);
            return new ExecutionResult(false, e.toString());
        }
        return new ExecutionResult(true, null);
    }

};