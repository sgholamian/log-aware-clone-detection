//,temp,TestNodeStatusUpdater.java,844,869,temp,TestNodeStatusUpdater.java,152,171
//,3
public class xxx {
    @Override
    public RegisterNodeManagerResponse registerNodeManager(
        RegisterNodeManagerRequest request) throws YarnException, IOException,
        IOException {
      if (System.currentTimeMillis() - waitStartTime <= rmStartIntervalMS
          || rmNeverStart) {
        throw new java.net.ConnectException("Faking RM start failure as start "
            + "delay timer has not expired.");
      } else {
        NodeId nodeId = request.getNodeId();
        Resource resource = request.getResource();
        LOG.info("Registering " + nodeId.toString());
        // NOTE: this really should be checking against the config value
        InetSocketAddress expected = NetUtils.getConnectAddress(
            conf.getSocketAddr(YarnConfiguration.NM_ADDRESS, null, -1));
        Assert.assertEquals(NetUtils.getHostPortString(expected),
            nodeId.toString());
        Assert.assertEquals(5 * 1024, resource.getMemorySize());
        registeredNodes.add(nodeId);

        RegisterNodeManagerResponse response = recordFactory
            .newRecordInstance(RegisterNodeManagerResponse.class);
        triggered = true;
        return response;
      }
    }

};