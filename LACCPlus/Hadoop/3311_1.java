//,temp,TestDecommissionWithStriped.java,184,188,temp,TestDecommissionWithStriped.java,159,163
//,2
public class xxx {
  @Test(timeout = 120000)
  public void testDecommissionTwoNodes() throws Exception {
    LOG.info("Starting test testDecommissionTwoNodes");
    testDecommission(blockSize * dataBlocks, 9, 2, "testDecommissionTwoNodes");
  }

};