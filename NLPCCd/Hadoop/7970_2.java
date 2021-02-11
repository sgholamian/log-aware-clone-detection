//,temp,sample_123.java,2,16,temp,sample_7449.java,2,16
//,3
public class xxx {
public void dummy_method(){
DataNode primaryDN = cluster.getDataNode(expectedPrimary.getIpcPort());
DatanodeProtocolClientSideTranslatorPB nnSpy = dnMap.get(primaryDN);
if (nnSpy == null) {
nnSpy = InternalDataNodeTestUtils.spyOnBposToNN(primaryDN, nn);
dnMap.put(primaryDN, nnSpy);
}
DelayAnswer delayer = new DelayAnswer(LOG);
Mockito.doAnswer(delayer).when(nnSpy).commitBlockSynchronization( Mockito.eq(blk), Mockito.anyInt(), Mockito.anyLong(), Mockito.eq(true), Mockito.eq(false), (DatanodeID[]) Mockito.anyObject(), (String[]) Mockito.anyObject());
fs.recoverLease(fPath);
delayer.waitForCall();


log.info("deleting recursively");
}

};