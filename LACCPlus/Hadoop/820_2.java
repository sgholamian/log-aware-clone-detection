//,temp,TestClientReportBadBlock.java,279,293,temp,TestClientReportBadBlock.java,257,274
//,3
public class xxx {
  private void dfsClientReadFile(Path corruptedFile) throws IOException,
      UnresolvedLinkException {
    DFSInputStream in = dfs.dfs.open(corruptedFile.toUri().getPath());
    byte[] buf = new byte[buffersize];
    int nRead = 0; // total number of bytes read
    
    try {
      do {
        nRead = in.read(buf, 0, buf.length);
      } while (nRead > 0);
    } catch (ChecksumException ce) {
      // caught ChecksumException if all replicas are bad, ignore and continue.
      LOG.debug("DfsClientReadFile caught ChecksumException.");
    } catch (BlockMissingException bme) {
      // caught BlockMissingException, ignore.
      LOG.debug("DfsClientReadFile caught BlockMissingException.");
    }
  }

};