//,temp,TestDataNodeMultipleRegistrations.java,146,198,temp,TestDataNodeMultipleRegistrations.java,65,133
//,3
public class xxx {
  @Test
  public void test2NNRegistration() throws IOException {
    MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf)
        .nnTopology(MiniDFSNNTopology.simpleFederatedTopology(2))
        .build();
    try {
      cluster.waitActive();
      NameNode nn1 = cluster.getNameNode(0);
      NameNode nn2 = cluster.getNameNode(1);
      assertNotNull("cannot create nn1", nn1);
      assertNotNull("cannot create nn2", nn2);

      String bpid1 = FSImageTestUtil.getFSImage(nn1).getBlockPoolID();
      String bpid2 = FSImageTestUtil.getFSImage(nn2).getBlockPoolID();
      String cid1 = FSImageTestUtil.getFSImage(nn1).getClusterID();
      String cid2 = FSImageTestUtil.getFSImage(nn2).getClusterID();
      int lv1 =FSImageTestUtil.getFSImage(nn1).getLayoutVersion();
      int lv2 = FSImageTestUtil.getFSImage(nn2).getLayoutVersion();
      int ns1 = FSImageTestUtil.getFSImage(nn1).getNamespaceID();
      int ns2 = FSImageTestUtil.getFSImage(nn2).getNamespaceID();
      assertNotSame("namespace ids should be different", ns1, ns2);
      LOG.info("nn1: lv=" + lv1 + ";cid=" + cid1 + ";bpid=" + bpid1 + ";uri="
          + nn1.getNameNodeAddress());
      LOG.info("nn2: lv=" + lv2 + ";cid=" + cid2 + ";bpid=" + bpid2 + ";uri="
          + nn2.getNameNodeAddress());

      // check number of volumes in fsdataset
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
        LOG.info("BP: " + bpos);
      }

      BPOfferService bpos1 = dn.getAllBpOs().get(0);
      BPOfferService bpos2 = dn.getAllBpOs().get(1);

      // The order of bpos is not guaranteed, so fix the order
      if (getNNSocketAddress(bpos1).equals(nn2.getNameNodeAddress())) {
        BPOfferService tmp = bpos1;
        bpos1 = bpos2;
        bpos2 = tmp;
      }

      assertEquals("wrong nn address", getNNSocketAddress(bpos1),
          nn1.getNameNodeAddress());
      assertEquals("wrong nn address", getNNSocketAddress(bpos2),
          nn2.getNameNodeAddress());
      assertEquals("wrong bpid", bpos1.getBlockPoolId(), bpid1);
      assertEquals("wrong bpid", bpos2.getBlockPoolId(), bpid2);
      assertEquals("wrong cid", dn.getClusterId(), cid1);
      assertEquals("cid should be same", cid2, cid1);
      assertEquals("namespace should be same",
          bpos1.bpNSInfo.namespaceID, ns1);
      assertEquals("namespace should be same",
          bpos2.bpNSInfo.namespaceID, ns2);
    } finally {
      cluster.shutdown();
    }
  }

};