//,temp,FSUtils.java,1160,1174,temp,FSUtils.java,865,873
//,3
public class xxx {
    @Override
    protected boolean accept(Path p, @CheckForNull Boolean isDir) {
      if (!StoreFileInfo.isReference(p)) {
        return false;
      }

      try {
        // only files can be references.
        return isFile(fs, isDir, p);
      } catch (IOException ioe) {
        // Maybe the file was moved or the fs was disconnected.
        LOG.warn("Skipping file " + p +" due to IOException", ioe);
        return false;
      }
    }

};