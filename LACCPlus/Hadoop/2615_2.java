//,temp,TestDecommissionWithStriped.java,178,182,temp,TestDecommissionWithStriped.java,159,163
//,3
public class xxx {
  @Test(timeout = 120000)
  public void testFileFullBlockGroup() throws Exception {
    LOG.info("Starting test testFileFullBlockGroup");
    testDecommission(blockSize * dataBlocks, 9, 1, "testFileFullBlockGroup");
  }

};