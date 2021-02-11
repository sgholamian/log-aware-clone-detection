//,temp,TestDecommissionWithStriped.java,178,182,temp,TestDecommissionWithStriped.java,159,163
//,3
public class xxx {
  @Test(timeout = 120000)
  public void testFileSmallerThanOneStripe() throws Exception {
    LOG.info("Starting test testFileSmallerThanOneStripe");
    testDecommission(cellSize * 2, 5, 1, "testFileSmallerThanOneStripe");
  }

};