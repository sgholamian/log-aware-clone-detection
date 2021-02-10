//,temp,WindowsSecureContainerExecutor.java,362,380,temp,WindowsSecureContainerExecutor.java,319,341
//,3
public class xxx {
      @Override
      protected boolean mkOneDirWithMode(Path path, File p2f,
          FsPermission permission) throws IOException {
        if (LOG.isDebugEnabled()) {
          LOG.debug(String.format("EFS:mkOneDirWithMode: %s %s", path,
              permission));
        }
        boolean ret = false;

        // File.mkdir returns false, does not throw. Must mimic it.
        try {
          Native.Elevated.mkdir(path);
          setPermission(path, permission);
          ret = true;
        }
        catch(Throwable e) {
          if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("EFS:mkOneDirWithMode: %s",
                org.apache.hadoop.util.StringUtils.stringifyException(e)));
          }
        }
        return ret;
      }

};