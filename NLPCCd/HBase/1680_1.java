//,temp,sample_4471.java,2,19,temp,sample_2123.java,2,19
//,3
public class xxx {
public void dummy_method(){
assertEquals("@Peer1 t2_syncup should be NOT sync up and have 100 rows", 100, rowCount_ht2TargetAtPeer1);
for (int i = 0; i < NB_RETRIES; i++) {
syncUp(utility1);
rowCount_ht1TargetAtPeer1 = utility2.countRows(ht1TargetAtPeer1);
rowCount_ht2TargetAtPeer1 = utility2.countRows(ht2TargetAtPeer1);
if (i == NB_RETRIES - 1) {
if (rowCount_ht1TargetAtPeer1 != 100 || rowCount_ht2TargetAtPeer1 != 200) {
utility1.restartHBaseCluster(1);
rowCount_ht1Source = utility1.countRows(ht1Source);
rowCount_ht2Source = utility1.countRows(ht2Source);


log.info("syncup should have rows at source and it is");
}
}
}
}

};