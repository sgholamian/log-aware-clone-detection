//,temp,TezSessionState.java,736,743,temp,TezSessionState.java,727,734
//,3
public class xxx {
  protected final void cleanupScratchDir() throws IOException {
    LOG.info("Attempting to clean up scratchDir for {} : {}", sessionId, tezScratchDir);
    if (tezScratchDir != null) {
      FileSystem fs = tezScratchDir.getFileSystem(conf);
      fs.delete(tezScratchDir, true);
      tezScratchDir = null;
    }
  }

};