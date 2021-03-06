//,temp,VmwareResource.java,1085,1099,temp,VmwareResource.java,1025,1039
//,2
public class xxx {
    protected ExecutionResult prepareNetworkElementCommand(SetupGuestNetworkCommand cmd) {
        NicTO nic = cmd.getNic();
        String routerIp = getRouterSshControlIp(cmd);
        String domrName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);

        try {
            int ethDeviceNum = findRouterEthDeviceIndex(domrName, routerIp, nic.getMac());
            nic.setDeviceId(ethDeviceNum);
        } catch (Exception e) {
            String msg = "Prepare SetupGuestNetwork failed due to " + e.toString();
            s_logger.warn(msg, e);
            return new ExecutionResult(false, msg);
        }
        return new ExecutionResult(true, null);
    }

};