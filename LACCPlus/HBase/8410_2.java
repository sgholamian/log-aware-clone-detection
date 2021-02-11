//,temp,FSUtils.java,1160,1174,temp,FSUtils.java,865,873
//,3
public class xxx {
    @Override
    protected boolean accept(Path p, @CheckForNull Boolean isDir) {
      try {
        return isFile(fs, isDir, p);
      } catch (IOException e) {
        LOG.warn("unable to verify if path=" + p + " is a regular file", e);
        return false;
      }
    }

};