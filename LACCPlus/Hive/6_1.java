//,temp,TestStatsReplicationScenarios.java,685,689,temp,TestStatsReplicationScenarios.java,679,683
//,3
public class xxx {
  @Test
  public void testForParallelBootstrapLoad() throws Throwable {
    LOG.info("Testing " + testName.getClass().getName() + "." + testName.getMethodName());
    testStatsReplicationCommon(true, false, false);
  }

};