//,temp,VmwareResource.java,1069,1083,temp,HypervDirectConnectResource.java,753,772
//,3
public class xxx {
    protected ExecutionResult prepareNetworkElementCommand(SetSourceNatCommand cmd) {
        String routerName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        String routerIp = getRouterSshControlIp(cmd);
        IpAddressTO pubIp = cmd.getIpAddress();

        try {
            int ethDeviceNum = findRouterEthDeviceIndex(routerName, routerIp, pubIp.getVifMacAddress());
            pubIp.setNicDevId(ethDeviceNum);
        } catch (Exception e) {
            String msg = "Prepare Ip SNAT failure due to " + e.toString();
            s_logger.error(msg, e);
            return new ExecutionResult(false, e.toString());
        }
        return new ExecutionResult(true, null);
    }

};