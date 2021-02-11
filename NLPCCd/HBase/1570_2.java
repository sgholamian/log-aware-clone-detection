//,temp,sample_2528.java,2,16,temp,sample_2530.java,2,16
//,3
public class xxx {
public void dummy_method(){
currentLeader = getCurrentLeader();
assertNotNull("New leader should exist after abdication", currentLeader);
znodeData = ZKUtil.getData(currentLeader.getWatcher(), LEADER_ZNODE);
assertNotNull("Leader znode should contain leader index", znodeData);
assertTrue("Leader znode should not be empty", znodeData.length > 0);
storedIndex = Bytes.toInt(znodeData);
assertEquals("Leader znode should match leader index", currentLeader.getIndex(), storedIndex);
currentLeader.stop("Stopping for test");
currentLeader = getCurrentLeader();
assertNotNull("New leader should exist after stop", currentLeader);


log.info("new leader index is");
}

};