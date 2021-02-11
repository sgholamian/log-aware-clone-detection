//,temp,TestClientReportBadBlock.java,282,296,temp,TestClientReportBadBlock.java,260,277
//,3
public class xxx {
  private void dfsClientReadFileFromPosition(Path corruptedFile)
      throws UnresolvedLinkException, IOException {
    DFSInputStream in = dfs.dfs.open(corruptedFile.toUri().getPath());
    byte[] buf = new byte[buffersize];
    int startPosition = 2;
    int nRead = 0; // total number of bytes read
    try {
      do {
        nRead = in.read(startPosition, buf, 0, buf.length);
        startPosition += buf.length;
      } while (nRead > 0);
    } catch (BlockMissingException bme) {
      LOG.debug("DfsClientReadFile caught BlockMissingException.");
    }
  }

};