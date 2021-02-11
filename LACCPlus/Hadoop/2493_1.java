//,temp,TestDataNodeMultipleRegistrations.java,146,198,temp,TestDataNodeMultipleRegistrations.java,65,133
//,3
public class xxx {
  @Test
  public void testFedSingleNN() throws IOException {
    MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
        .nameNodePort(9927).build();
    try {
      NameNode nn1 = cluster.getNameNode();
      assertNotNull("cannot create nn1", nn1);

      String bpid1 = FSImageTestUtil.getFSImage(nn1).getBlockPoolID();
      String cid1 = FSImageTestUtil.getFSImage(nn1).getClusterID();
      int lv1 = FSImageTestUtil.getFSImage(nn1).getLayoutVersion();
      LOG.info("nn1: lv=" + lv1 + ";cid=" + cid1 + ";bpid=" + bpid1 + ";uri="
          + nn1.getNameNodeAddress());

      // check number of vlumes in fsdataset
      DataNode dn = cluster.getDataNodes().get(0);
      final Map<String, Object> volInfos = dn.data.getVolumeInfoMap();
      Assert.assertTrue("No volumes in the fsdataset", volInfos.size() > 0);
      int i = 0;
      for (Map.Entry<String, Object> e : volInfos.entrySet()) {
        LOG.info("vol " + i++ + ") " + e.getKey() + ": " + e.getValue());
      }
      // number of volumes should be 2 - [data1, data2]
      assertEquals("number of volumes is wrong",
          cluster.getFsDatasetTestUtils(0).getDefaultNumOfDataDirs(),
          volInfos.size());

      for (BPOfferService bpos : dn.getAllBpOs()) {
        LOG.info("reg: bpid=" + "; name=" + bpos.bpRegistration + "; sid="
            + bpos.bpRegistration.getDatanodeUuid() + "; nna=" +
            getNNSocketAddress(bpos));
      }

      // try block report
      BPOfferService bpos1 = dn.getAllBpOs().get(0);
      bpos1.triggerBlockReportForTests();

      assertEquals("wrong nn address",
          getNNSocketAddress(bpos1),
          nn1.getNameNodeAddress());
      assertEquals("wrong bpid", bpos1.getBlockPoolId(), bpid1);
      assertEquals("wrong cid", dn.getClusterId(), cid1);
      cluster.shutdown();
      
      // Ensure all the BPOfferService threads are shutdown
      assertEquals(0, dn.getAllBpOs().size());
      cluster = null;
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};