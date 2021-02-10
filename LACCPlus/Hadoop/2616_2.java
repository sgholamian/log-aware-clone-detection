//,temp,TestDecommissionWithStriped.java,184,188,temp,TestDecommissionWithStriped.java,165,170
//,3
public class xxx {
  @Test(timeout = 120000)
  public void testFileMultipleBlockGroups() throws Exception {
    LOG.info("Starting test testFileMultipleBlockGroups");
    int writeBytes = 2 * blockSize * dataBlocks;
    testDecommission(writeBytes, 9, 1, "testFileMultipleBlockGroups");
  }

};