//,temp,TestClientReportBadBlock.java,336,342,temp,TestClientReportBadBlock.java,330,334
//,3
public class xxx {
  private static void testFsckListCorruptFilesBlocks(Path filePath, int errorCode) throws Exception{
    String outStr = runFsck(conf, errorCode, true, filePath.toString(), "-list-corruptfileblocks");
    LOG.info("fsck -list-corruptfileblocks out: " + outStr);
    if (errorCode != 0) {
      Assert.assertTrue(outStr.contains("CORRUPT files"));
    }
  }

};