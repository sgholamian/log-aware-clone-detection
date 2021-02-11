//,temp,TestClientReportBadBlock.java,336,342,temp,TestClientReportBadBlock.java,330,334
//,3
public class xxx {
  private static void verifyFsckBlockCorrupted() throws Exception {
    String outStr = runFsck(conf, 1, true, "/");
    LOG.info(outStr);
    Assert.assertTrue(outStr.contains(NamenodeFsck.CORRUPT_STATUS));
  }

};