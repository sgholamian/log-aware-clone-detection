//,temp,TestClientRMService.java,1463,1545,temp,TestClientRMService.java,1400,1461
//,3
public class xxx {
  @Test
  public void testGetNodeLabels() throws Exception {
    MockRM rm = new MockRM() {
      protected ClientRMService createClientRMService() {
        return new ClientRMService(this.rmContext, scheduler,
            this.rmAppManager, this.applicationACLsManager,
            this.queueACLsManager, this.getRMContext()
                .getRMDelegationTokenSecretManager());
      };
    };
    rm.start();
    NodeLabel labelX = NodeLabel.newInstance("x", false);
    NodeLabel labelY = NodeLabel.newInstance("y");
    RMNodeLabelsManager labelsMgr = rm.getRMContext().getNodeLabelManager();
    labelsMgr.addToCluserNodeLabels(ImmutableSet.of(labelX, labelY));

    NodeId node1 = NodeId.newInstance("host1", 1234);
    NodeId node2 = NodeId.newInstance("host2", 1234);
    Map<NodeId, Set<String>> map = new HashMap<NodeId, Set<String>>();
    map.put(node1, ImmutableSet.of("x"));
    map.put(node2, ImmutableSet.of("y"));
    labelsMgr.replaceLabelsOnNode(map);

    // Create a client.
    Configuration conf = new Configuration();
    YarnRPC rpc = YarnRPC.create(conf);
    InetSocketAddress rmAddress = rm.getClientRMService().getBindAddress();
    LOG.info("Connecting to ResourceManager at " + rmAddress);
    ApplicationClientProtocol client = (ApplicationClientProtocol) rpc
        .getProxy(ApplicationClientProtocol.class, rmAddress, conf);

    // Get node labels collection
    GetClusterNodeLabelsResponse response = client
        .getClusterNodeLabels(GetClusterNodeLabelsRequest.newInstance());
    Assert.assertTrue(response.getNodeLabels().containsAll(
        Arrays.asList(labelX, labelY)));

    // Get node labels mapping
    GetNodesToLabelsResponse response1 = client
        .getNodeToLabels(GetNodesToLabelsRequest.newInstance());
    Map<NodeId, Set<NodeLabel>> nodeToLabels = response1.getNodeToLabels();
    Assert.assertTrue(nodeToLabels.keySet().containsAll(
        Arrays.asList(node1, node2)));
    Assert.assertTrue(nodeToLabels.get(node1)
        .containsAll(Arrays.asList(labelX)));
    Assert.assertTrue(nodeToLabels.get(node2)
        .containsAll(Arrays.asList(labelY)));
    // Verify whether labelX's exclusivity is false
    for (NodeLabel x : nodeToLabels.get(node1)) {
      Assert.assertFalse(x.isExclusive());
    }
    // Verify whether labelY's exclusivity is true
    for (NodeLabel y : nodeToLabels.get(node2)) {
      Assert.assertTrue(y.isExclusive());
    }
    // Below label "x" is not present in the response as exclusivity is true
    Assert.assertFalse(nodeToLabels.get(node1).containsAll(
        Arrays.asList(NodeLabel.newInstance("x"))));

    rpc.stopProxy(client, conf);
    rm.close();
  }

};