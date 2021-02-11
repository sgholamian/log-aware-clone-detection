//,temp,sample_123.java,2,16,temp,sample_7449.java,2,16
//,3
public class xxx {
public void dummy_method(){
NameNode nn0 = cluster.getNameNode(0);
ExtendedBlock blk = DFSTestUtil.getFirstBlock(fs, TEST_PATH);
DatanodeDescriptor expectedPrimary = DFSTestUtil.getExpectedPrimaryNode(nn0, blk);
DataNode primaryDN = cluster.getDataNode(expectedPrimary.getIpcPort());
DatanodeProtocolClientSideTranslatorPB nnSpy = InternalDataNodeTestUtils.spyOnBposToNN(primaryDN, nn0);
DelayAnswer delayer = new DelayAnswer(LOG);
Mockito.doAnswer(delayer).when(nnSpy).commitBlockSynchronization( Mockito.eq(blk), Mockito.anyInt(), Mockito.anyLong(), Mockito.eq(true), Mockito.eq(false), (DatanodeID[]) Mockito.anyObject(), (String[]) Mockito.anyObject());
DistributedFileSystem fsOtherUser = createFsAsOtherUser(cluster, conf);
assertFalse(fsOtherUser.recoverLease(TEST_PATH));
delayer.waitForCall();


log.info("failing over to nn");
}

};