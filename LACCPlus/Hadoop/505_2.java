//,temp,MiniDFSClusterWithNodeGroup.java,52,208,temp,MiniDFSCluster.java,1334,1516
//,3
public class xxx {
  public synchronized void startDataNodes(Configuration conf, int numDataNodes,
      StorageType[][] storageTypes, boolean manageDfsDirs, StartupOption operation,
      String[] racks, String[] hosts,
      long[][] storageCapacities,
      long[] simulatedCapacities,
      boolean setupHostsFile,
      boolean checkDataNodeAddrConfig,
      boolean checkDataNodeHostConfig,
      Configuration[] dnConfOverlays) throws IOException {
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

    int curDatanodesNum = dataNodes.size();
    final int curDatanodesNumSaved = curDatanodesNum;
    // for mincluster's the default initialDelay for BRs is 0
    if (conf.get(DFS_BLOCKREPORT_INITIAL_DELAY_KEY) == null) {
      conf.setLong(DFS_BLOCKREPORT_INITIAL_DELAY_KEY, 0);
    }
    // If minicluster's name node is null assume that the conf has been
    // set with the right address:port of the name node.
    //
    if (racks != null && numDataNodes > racks.length ) {
      throw new IllegalArgumentException( "The length of racks [" + racks.length
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
    
    if (dnConfOverlays != null
        && numDataNodes > dnConfOverlays.length) {
      throw new IllegalArgumentException( "The length of dnConfOverlays [" 
          + dnConfOverlays.length
          + "] is less than the number of datanodes [" + numDataNodes + "].");
    }

    String [] dnArgs = (operation == null ||
                        operation != StartupOption.ROLLBACK) ?
        null : new String[] {operation.getName()};
    
    DataNode[] dns = new DataNode[numDataNodes];
    for (int i = curDatanodesNum; i < curDatanodesNum+numDataNodes; i++) {
      Configuration dnConf = new HdfsConfiguration(conf);
      if (dnConfOverlays != null) {
        dnConf.addResource(dnConfOverlays[i]);
      }
      // Set up datanode address
      setupDatanodeAddress(dnConf, setupHostsFile, checkDataNodeAddrConfig);
      if (manageDfsDirs) {
        String dirs = makeDataNodeDirs(i, storageTypes == null ?
          null : storageTypes[i - curDatanodesNum]);
        dnConf.set(DFS_DATANODE_DATA_DIR_KEY, dirs);
        conf.set(DFS_DATANODE_DATA_DIR_KEY, dirs);
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
        LOG.info("Adding node with hostname : " + name + " to rack " +
                            racks[i-curDatanodesNum]);
        StaticMapping.addNodeToRack(name,
                                    racks[i-curDatanodesNum]);
      }
      Configuration newconf = new HdfsConfiguration(dnConf); // save config
      if (hosts != null) {
        NetUtils.addStaticResolution(hosts[i - curDatanodesNum], "localhost");
      }

      SecureResources secureResources = null;
      if (UserGroupInformation.isSecurityEnabled() &&
          conf.get(DFS_DATA_TRANSFER_PROTECTION_KEY) == null) {
        try {
          secureResources = SecureDataNodeStarter.getSecureResources(dnConf);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      final int maxRetriesOnSasl = conf.getInt(
        IPC_CLIENT_CONNECT_MAX_RETRIES_ON_SASL_KEY,
        IPC_CLIENT_CONNECT_MAX_RETRIES_ON_SASL_DEFAULT);
      int numRetries = 0;
      DataNode dn = null;
      while (true) {
        try {
          dn = DataNode.instantiateDataNode(dnArgs, dnConf,
                                            secureResources);
          break;
        } catch (IOException e) {
          // Work around issue testing security where rapidly starting multiple
          // DataNodes using the same principal gets rejected by the KDC as a
          // replay attack.
          if (UserGroupInformation.isSecurityEnabled() &&
              numRetries < maxRetriesOnSasl) {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException ie) {
              Thread.currentThread().interrupt();
              break;
            }
            ++numRetries;
            continue;
          }
          throw e;
        }
      }
      if(dn == null)
        throw new IOException("Cannot start DataNode in "
            + dnConf.get(DFS_DATANODE_DATA_DIR_KEY));
      //since the HDFS does things based on host|ip:port, we need to add the
      //mapping for the service to rackId
      String service =
          SecurityUtil.buildTokenService(dn.getXferAddress()).toString();
      if (racks != null) {
        LOG.info("Adding node with service : " + service +
                            " to rack " + racks[i-curDatanodesNum]);
        StaticMapping.addNodeToRack(service,
                                  racks[i-curDatanodesNum]);
      }
      dn.runDatanodeDaemon();
      dataNodes.add(new DataNodeProperties(dn, newconf, dnArgs,
          secureResources, dn.getIpcPort()));
      dns[i - curDatanodesNum] = dn;
    }
    this.numDataNodes += numDataNodes;
    waitActive();
    
    if (storageCapacities != null) {
      for (int i = curDatanodesNumSaved; i < curDatanodesNumSaved+numDataNodes; ++i) {
        final int index = i - curDatanodesNum;
        try (FsDatasetSpi.FsVolumeReferences volumes =
            dns[index].getFSDataset().getFsVolumeReferences()) {
          assert storageCapacities[index].length == storagesPerDatanode;
          assert volumes.size() == storagesPerDatanode;

          int j = 0;
          for (FsVolumeSpi fvs : volumes) {
            FsVolumeImpl volume = (FsVolumeImpl) fvs;
            LOG.info("setCapacityForTesting " + storageCapacities[index][j]
                + " for [" + volume.getStorageType() + "]" + volume
                .getStorageID());
            volume.setCapacityForTesting(storageCapacities[index][j]);
            j++;
          }
        }
      }
    }
  }

};