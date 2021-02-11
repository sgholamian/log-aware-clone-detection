//,temp,sample_6306.java,2,16,temp,sample_6305.java,2,16
//,3
public class xxx {
public void dummy_method(){
cluster.restartDataNode(stoppedDN1, true);
do {
dnID = cluster.getDataNodes().get(1).getDatanodeId();
} while (dnID == null);
dnDescriptor1 = dm.getDatanode(dnID);
while (dnDescriptor1.numBlocks() == 0) {
Thread.sleep(100);
}
Thread.sleep(2000);
assertEquals(underreplicated, bm.getUnderReplicatedBlocksCount());


log.info("starting two more nodes");
}

};