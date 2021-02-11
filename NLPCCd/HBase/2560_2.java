//,temp,sample_973.java,2,16,temp,sample_970.java,2,16
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < masterThreads.size(); i++) {
if (masterThreads.get(i).getMaster().isActiveMaster()) {
assertTrue(activeName.equals(masterThreads.get(i).getMaster().getServerName()));
activeIndex = i;
active = masterThreads.get(activeIndex).getMaster();
}
}
assertEquals(1, numActive);
assertEquals(2, masterThreads.size());
int rsCount = masterThreads.get(activeIndex).getMaster().getClusterMetrics() .getLiveServerMetrics().size();


log.info("active master managing regions servers");
}

};