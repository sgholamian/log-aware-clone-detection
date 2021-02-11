//,temp,sample_973.java,2,16,temp,sample_970.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertTrue(cluster.waitForActiveAndReadyMaster());
assertEquals(1, masterThreads.size());
active = masterThreads.get(0).getMaster();
assertNotNull(active);
status = active.getClusterMetrics();
ServerName mastername = status.getMasterName();
assertTrue(mastername.equals(active.getServerName()));
assertTrue(active.isActiveMaster());
assertEquals(0, status.getBackupMasterNames().size());
int rss = status.getLiveServerMetrics().size();


log.info("active master managing region servers");
}

};