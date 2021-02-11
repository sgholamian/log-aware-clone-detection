//,temp,sample_1283.java,2,13,temp,sample_267.java,2,14
//,3
public class xxx {
public void setup() throws Exception {
TEST_UTIL.startMiniZKCluster();
Configuration conf = TEST_UTIL.getConfiguration();
zkw = new ZKWatcher(TEST_UTIL.getConfiguration(), "split-log-worker-tests", null);
ds = new DummyServer(zkw, conf);
ZKUtil.deleteChildrenRecursively(zkw, zkw.znodePaths.baseZNode);
ZKUtil.createAndFailSilent(zkw, zkw.znodePaths.baseZNode);
assertThat(ZKUtil.checkExists(zkw, zkw.znodePaths.baseZNode), not (is(-1)));


log.info("created");
}

};