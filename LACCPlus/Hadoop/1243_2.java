//,temp,PBImageTextWriter.java,575,588,temp,PBImageTextWriter.java,532,547
//,3
public class xxx {
  private void loadDirectoriesInINodeSection(InputStream in) throws IOException {
    INodeSection s = INodeSection.parseDelimitedFrom(in);
    LOG.info("Loading directories in INode section.");
    int numDirs = 0;
    for (int i = 0; i < s.getNumInodes(); ++i) {
      INode p = INode.parseDelimitedFrom(in);
      if (LOG.isDebugEnabled() && i % 10000 == 0) {
        LOG.debug("Scanned {} inodes.", i);
      }
      if (p.hasDirectory()) {
        metadataMap.putDir(p);
        numDirs++;
      }
    }
    LOG.info("Found {} directories in INode section.", numDirs);
  }

};