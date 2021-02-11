//,temp,TestClientRMService.java,1463,1545,temp,TestClientRMService.java,1400,1461
//,3
public class xxx {
  @Test
  public void testGetLabelsToNodes() throws Exception {
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
    NodeLabel labelY = NodeLabel.newInstance("y", false);
    NodeLabel labelZ = NodeLabel.newInstance("z", false);
    RMNodeLabelsManager labelsMgr = rm.getRMContext().getNodeLabelManager();
    labelsMgr.addToCluserNodeLabels(ImmutableSet.of(labelX, labelY, labelZ));

    NodeId node1A = NodeId.newInstance("host1", 1234);
    NodeId node1B = NodeId.newInstance("host1", 5678);
    NodeId node2A = NodeId.newInstance("host2", 1234);
    NodeId node3A = NodeId.newInstance("host3", 1234);
    NodeId node3B = NodeId.newInstance("host3", 5678);
    Map<NodeId, Set<String>> map = new HashMap<NodeId, Set<String>>();
    map.put(node1A, ImmutableSet.of("x"));
    map.put(node1B, ImmutableSet.of("z"));
    map.put(node2A, ImmutableSet.of("y"));
    map.put(node3A, ImmutableSet.of("y"));
    map.put(node3B, ImmutableSet.of("z"));
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
        Arrays.asList(labelX, labelY, labelZ)));

    // Get labels to nodes mapping
    GetLabelsToNodesResponse response1 = client
        .getLabelsToNodes(GetLabelsToNodesRequest.newInstance());
    Map<NodeLabel, Set<NodeId>> labelsToNodes = response1.getLabelsToNodes();
    // Verify whether all NodeLabel's exclusivity are false
    for (Map.Entry<NodeLabel, Set<NodeId>> nltn : labelsToNodes.entrySet()) {
      Assert.assertFalse(nltn.getKey().isExclusive());
    }
    Assert.assertTrue(labelsToNodes.keySet().containsAll(
        Arrays.asList(labelX, labelY, labelZ)));
    Assert.assertTrue(labelsToNodes.get(labelX).containsAll(
        Arrays.asList(node1A)));
    Assert.assertTrue(labelsToNodes.get(labelY).containsAll(
        Arrays.asList(node2A, node3A)));
    Assert.assertTrue(labelsToNodes.get(labelZ).containsAll(
        Arrays.asList(node1B, node3B)));

    // Get labels to nodes mapping for specific labels
    Set<String> setlabels = new HashSet<String>(Arrays.asList(new String[]{"x",
        "z"}));
    GetLabelsToNodesResponse response2 = client
        .getLabelsToNodes(GetLabelsToNodesRequest.newInstance(setlabels));
    labelsToNodes = response2.getLabelsToNodes();
    // Verify whether all NodeLabel's exclusivity are false
    for (Map.Entry<NodeLabel, Set<NodeId>> nltn : labelsToNodes.entrySet()) {
      Assert.assertFalse(nltn.getKey().isExclusive());
    }
    Assert.assertTrue(labelsToNodes.keySet().containsAll(
        Arrays.asList(labelX, labelZ)));
    Assert.assertTrue(labelsToNodes.get(labelX).containsAll(
        Arrays.asList(node1A)));
    Assert.assertTrue(labelsToNodes.get(labelZ).containsAll(
        Arrays.asList(node1B, node3B)));
    Assert.assertEquals(labelsToNodes.get(labelY), null);

    rpc.stopProxy(client, conf);
    rm.close();
  }

};