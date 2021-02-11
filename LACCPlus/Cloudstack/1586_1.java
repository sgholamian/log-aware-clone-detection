//,temp,LibvirtComputingResource.java,1482,1496,temp,LibvirtComputingResource.java,1378,1388
//,3
public class xxx {
    public synchronized boolean destroyTunnelNetwork(final String bridge) {
        findOrCreateTunnelNetwork(bridge);

        final Script cmd = new Script(_ovsTunnelPath, _timeout, s_logger);
        cmd.add("destroy_ovs_bridge");
        cmd.add("--bridge", bridge);

        final String result = cmd.execute();

        if (result != null) {
            s_logger.debug("OVS Bridge could not be destroyed due to error ==> " + result);
            return false;
        }
        return true;
    }

};