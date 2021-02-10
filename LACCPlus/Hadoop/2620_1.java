//,temp,TestDecommissionWithStriped.java,172,176,temp,TestDecommissionWithStriped.java,165,170
//,3
public class xxx {
  @Test(timeout = 120000)
  public void testFileSmallerThanOneCell() throws Exception {
    LOG.info("Starting test testFileSmallerThanOneCell");
    testDecommission(cellSize - 1, 4, 1, "testFileSmallerThanOneCell");
  }

};