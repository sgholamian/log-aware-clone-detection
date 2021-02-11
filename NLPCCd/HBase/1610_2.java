//,temp,sample_1283.java,2,13,temp,sample_267.java,2,14
//,3
public class xxx {
public void setup() throws Exception {
TEST_UTIL = new HBaseTestingUtility();
TEST_UTIL.startMiniZKCluster();
conf = TEST_UTIL.getConfiguration();
zkw = new ZKWatcher(conf, "split-log-manager-tests" + UUID.randomUUID().toString(), null);
master = new DummyMasterServices(zkw, conf);
ZKUtil.deleteChildrenRecursively(zkw, zkw.znodePaths.baseZNode);
ZKUtil.createAndFailSilent(zkw, zkw.znodePaths.baseZNode);
assertTrue(ZKUtil.checkExists(zkw, zkw.znodePaths.baseZNode) != -1);


log.info("created");
}

};