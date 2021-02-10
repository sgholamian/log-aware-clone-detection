//,temp,TestDatanodeRegistration.java,259,310,temp,TestDatanodeRegistration.java,210,257
//,3
public class xxx {
  @Test
  public void testRegistrationWithDifferentSoftwareVersionsDuringUpgrade()
      throws Exception {
    Configuration conf = new HdfsConfiguration();
    conf.set(DFSConfigKeys.DFS_DATANODE_MIN_SUPPORTED_NAMENODE_VERSION_KEY, "1.0.0");
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
      doReturn("fake-storage-id").when(mockDnReg).getDatanodeUuid();
      doReturn(mockStorageInfo).when(mockDnReg).getStorageInfo();
      
      // Should succeed when software versions are the same and CTimes are the
      // same.
      doReturn(VersionInfo.getVersion()).when(mockDnReg).getSoftwareVersion();
      doReturn("127.0.0.1").when(mockDnReg).getIpAddr();
      doReturn(123).when(mockDnReg).getXferPort();
      rpcServer.registerDatanode(mockDnReg);
      
      // Should succeed when software versions are the same and CTimes are
      // different.
      doReturn(nnCTime + 1).when(mockStorageInfo).getCTime();
      rpcServer.registerDatanode(mockDnReg);
      
      // Should fail when software version of DN is different from NN and CTimes
      // are different.
      doReturn(VersionInfo.getVersion() + ".1").when(mockDnReg).getSoftwareVersion();
      try {
        rpcServer.registerDatanode(mockDnReg);
        fail("Should not have been able to register DN with different software" +
            " versions and CTimes");
      } catch (IncorrectVersionException ive) {
        GenericTestUtils.assertExceptionContains(
            "does not match CTime of NN", ive);
        LOG.info("Got expected exception", ive);
      }
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};