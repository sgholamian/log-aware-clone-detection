//,temp,TestDatanodeRegistration.java,259,310,temp,TestDatanodeRegistration.java,210,257
//,3
public class xxx {
  @Test
  public void testRegistrationWithDifferentSoftwareVersions() throws Exception {
    Configuration conf = new HdfsConfiguration();
    conf.set(DFSConfigKeys.DFS_DATANODE_MIN_SUPPORTED_NAMENODE_VERSION_KEY, "3.0.0");
    conf.set(DFSConfigKeys.DFS_NAMENODE_MIN_SUPPORTED_DATANODE_VERSION_KEY, "3.0.0");
    MiniDFSCluster cluster = null;
    try {
      cluster = new MiniDFSCluster.Builder(conf)
          .numDataNodes(0)
          .build();
      
      NamenodeProtocols rpcServer = cluster.getNameNodeRpc();
      
      long nnCTime = cluster.getNamesystem().getFSImage().getStorage().getCTime();
      StorageInfo mockStorageInfo = mock(StorageInfo.class);
      doReturn(nnCTime).when(mockStorageInfo).getCTime();
      
      DatanodeRegistration mockDnReg = mock(DatanodeRegistration.class);
      doReturn(HdfsServerConstants.DATANODE_LAYOUT_VERSION).when(mockDnReg).getVersion();
      doReturn("127.0.0.1").when(mockDnReg).getIpAddr();
      doReturn(123).when(mockDnReg).getXferPort();
      doReturn("fake-storage-id").when(mockDnReg).getDatanodeUuid();
      doReturn(mockStorageInfo).when(mockDnReg).getStorageInfo();
      
      // Should succeed when software versions are the same.
      doReturn("3.0.0").when(mockDnReg).getSoftwareVersion();
      rpcServer.registerDatanode(mockDnReg);
      
      // Should succeed when software version of DN is above minimum required by NN.
      doReturn("4.0.0").when(mockDnReg).getSoftwareVersion();
      rpcServer.registerDatanode(mockDnReg);
      
      // Should fail when software version of DN is below minimum required by NN.
      doReturn("2.0.0").when(mockDnReg).getSoftwareVersion();
      try {
        rpcServer.registerDatanode(mockDnReg);
        fail("Should not have been able to register DN with too-low version.");
      } catch (IncorrectVersionException ive) {
        GenericTestUtils.assertExceptionContains(
            "The reported DataNode version is too low", ive);
        LOG.info("Got expected exception", ive);
      }
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};