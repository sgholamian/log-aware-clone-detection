//,temp,TestStatsReplicationScenarios.java,685,689,temp,TestStatsReplicationScenarios.java,679,683
//,3
public class xxx {
  @Test
  public void testNonParallelBootstrapLoad() throws Throwable {
    LOG.info("Testing " + testName.getClass().getName() + "." + testName.getMethodName());
    testStatsReplicationCommon(false, false, false);
  }

};