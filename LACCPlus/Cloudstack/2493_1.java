//,temp,CitrixResourceBase.java,4357,4374,temp,HypervDirectConnectResource.java,753,772
//,3
public class xxx {
    protected ExecutionResult prepareNetworkElementCommand(final SetSourceNatCommand cmd) {
        final Connection conn = getConnection();
        final String routerName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        final IpAddressTO pubIp = cmd.getIpAddress();
        try {
            final VM router = getVM(conn, routerName);

            final VIF correctVif = getCorrectVif(conn, router, pubIp);

            pubIp.setNicDevId(Integer.valueOf(correctVif.getDevice(conn)));

        } catch (final Exception e) {
            final String msg = "Ip SNAT failure due to " + e.toString();
            s_logger.error(msg, e);
            return new ExecutionResult(false, msg);
        }
        return new ExecutionResult(true, null);
    }

};