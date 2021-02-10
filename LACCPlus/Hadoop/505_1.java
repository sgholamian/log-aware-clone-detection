//,temp,MiniDFSClusterWithNodeGroup.java,52,208,temp,MiniDFSCluster.java,1334,1516
//,3
public class xxx {
  public synchronized void startDataNodes(Configuration conf, int numDataNodes,
      StorageType[][] storageTypes, boolean manageDfsDirs, StartupOption operation,
      String[] racks, String[] nodeGroups, String[] hosts,
      long[][] storageCapacities,
      long[] simulatedCapacities,
      boolean setupHostsFile,
      boolean checkDataNodeAddrConfig,
      boolean checkDataNodeHostConfig) throws IOException {

    assert storageCapacities == null || simulatedCapacities == null;
    assert storageTypes == null || storageTypes.length == numDataNodes;
    assert storageCapacities == null || storageCapacities.length == numDataNodes;

    if (operation == StartupOption.RECOVER) {
      return;
    }
    if (checkDataNodeHostConfig) {
      conf.setIfUnset(DFS_DATANODE_HOST_NAME_KEY, "127.0.0.1");
    } else {
      conf.set(DFS_DATANODE_HOST_NAME_KEY, "127.0.0.1");
    }
    conf.set(DFSConfigKeys.DFS_DATANODE_HOST_NAME_KEY, "127.0.0.1");

    int curDatanodesNum = dataNodes.size();
    // for mincluster's the default initialDelay for BRs is 0
    if (conf.get(DFSConfigKeys.DFS_BLOCKREPORT_INITIAL_DELAY_KEY) == null) {
      conf.setLong(DFSConfigKeys.DFS_BLOCKREPORT_INITIAL_DELAY_KEY, 0);
    }
    // If minicluster's name node is null assume that the conf has been
    // set with the right address:port of the name node.
    //
    if (racks != null && numDataNodes > racks.length ) {
      throw new IllegalArgumentException( "The length of racks [" + racks.length
          + "] is less than the number of datanodes [" + numDataNodes + "].");
    }

    if (nodeGroups != null && numDataNodes > nodeGroups.length ) {
      throw new IllegalArgumentException( "The length of nodeGroups [" + nodeGroups.length
          + "] is less than the number of datanodes [" + numDataNodes + "].");
    }

    if (hosts != null && numDataNodes > hosts.length ) {
      throw new IllegalArgumentException( "The length of hosts [" + hosts.length
          + "] is less than the number of datanodes [" + numDataNodes + "].");
    }
    //Generate some hostnames if required
    if (racks != null && hosts == null) {
      hosts = new String[numDataNodes];
      for (int i = curDatanodesNum; i < curDatanodesNum + numDataNodes; i++) {
        hosts[i - curDatanodesNum] = "host" + i + ".foo.com";
      }
    }

    if (simulatedCapacities != null 
        && numDataNodes > simulatedCapacities.length) {
      throw new IllegalArgumentException( "The length of simulatedCapacities [" 
          + simulatedCapacities.length
          + "] is less than the number of datanodes [" + numDataNodes + "].");
    }

    String [] dnArgs = (operation == null ||
    operation != StartupOption.ROLLBACK) ?
        null : new String[] {operation.getName()};

    DataNode[] dns = new DataNode[numDataNodes];
    for (int i = curDatanodesNum; i < curDatanodesNum+numDataNodes; i++) {
      Configuration dnConf = new HdfsConfiguration(conf);
      // Set up datanode address
      setupDatanodeAddress(dnConf, setupHostsFile, checkDataNodeAddrConfig);
      if (manageDfsDirs) {
        String dirs = makeDataNodeDirs(i, storageTypes == null ? null : storageTypes[i]);
        dnConf.set(DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY, dirs);
        conf.set(DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY, dirs);
      }
      if (simulatedCapacities != null) {
        SimulatedFSDataset.setFactory(dnConf);
        dnConf.setLong(SimulatedFSDataset.CONFIG_PROPERTY_CAPACITY,
        simulatedCapacities[i-curDatanodesNum]);
      }
      LOG.info("Starting DataNode " + i + " with "
          + DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY + ": "
          + dnConf.get(DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY));
      if (hosts != null) {
        dnConf.set(DFSConfigKeys.DFS_DATANODE_HOST_NAME_KEY, hosts[i - curDatanodesNum]);
        LOG.info("Starting DataNode " + i + " with hostname set to: "
            + dnConf.get(DFSConfigKeys.DFS_DATANODE_HOST_NAME_KEY));
      }
      if (racks != null) {
        String name = hosts[i - curDatanodesNum];
        if (nodeGroups == null) {
          LOG.info("Adding node with hostname : " + name + " to rack " +
             racks[i-curDatanodesNum]);
          StaticMapping.addNodeToRack(name,racks[i-curDatanodesNum]);
        } else {
          LOG.info("Adding node with hostname : " + name + " to serverGroup " +
              nodeGroups[i-curDatanodesNum] + " and rack " +
              racks[i-curDatanodesNum]);
          StaticMapping.addNodeToRack(name,racks[i-curDatanodesNum] + 
              nodeGroups[i-curDatanodesNum]);
        }
      }
      Configuration newconf = new HdfsConfiguration(dnConf); // save config
      if (hosts != null) {
        NetUtils.addStaticResolution(hosts[i - curDatanodesNum], "localhost");
      }
      
      SecureResources secureResources = null;
      if (UserGroupInformation.isSecurityEnabled()) {
        try {
          secureResources = SecureDataNodeStarter.getSecureResources(dnConf);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      DataNode dn = DataNode.instantiateDataNode(dnArgs, dnConf, secureResources);
      if(dn == null)
        throw new IOException("Cannot start DataNode in "
          + dnConf.get(DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY));
      //since the HDFS does things based on IP:port, we need to add the mapping
      //for IP:port to rackId
      String ipAddr = dn.getXferAddress().getAddress().getHostAddress();
      if (racks != null) {
        int port = dn.getXferAddress().getPort();
        if (nodeGroups == null) {
          LOG.info("Adding node with IP:port : " + ipAddr + ":" + port +
              " to rack " + racks[i-curDatanodesNum]);
          StaticMapping.addNodeToRack(ipAddr + ":" + port,
              racks[i-curDatanodesNum]);
        } else {
          LOG.info("Adding node with IP:port : " + ipAddr + ":" + port + " to nodeGroup " +
          nodeGroups[i-curDatanodesNum] + " and rack " + racks[i-curDatanodesNum]);
          StaticMapping.addNodeToRack(ipAddr + ":" + port, racks[i-curDatanodesNum] + 
              nodeGroups[i-curDatanodesNum]);
        }
      }
      dn.runDatanodeDaemon();
      dataNodes.add(new DataNodeProperties(dn, newconf, dnArgs, secureResources, dn.getIpcPort()));
      dns[i - curDatanodesNum] = dn;
    }
    curDatanodesNum += numDataNodes;
    this.numDataNodes += numDataNodes;
    waitActive();

    if (storageCapacities != null) {
      for (int i = curDatanodesNum; i < curDatanodesNum+numDataNodes; ++i) {
        try (FsDatasetSpi.FsVolumeReferences volumes =
            dns[i].getFSDataset().getFsVolumeReferences()) {
          assert volumes.size() == storagesPerDatanode;

          for (int j = 0; j < volumes.size(); ++j) {
            FsVolumeImpl volume = (FsVolumeImpl) volumes.get(j);
            volume.setCapacityForTesting(storageCapacities[i][j]);
          }
        }
      }
    }
  }

};