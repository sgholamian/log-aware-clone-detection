//,temp,WindowsSecureContainerExecutor.java,362,380,temp,WindowsSecureContainerExecutor.java,319,341
//,3
public class xxx {
      @Override
      protected OutputStream createOutputStreamWithMode(Path f, boolean append,
          FsPermission permission) throws IOException {
        if (LOG.isDebugEnabled()) {
          LOG.debug(String.format("EFS:createOutputStreamWithMode: %s %b %s", f,
              append, permission));
        }
        boolean success = false;
        OutputStream os = Native.Elevated.create(f, append);
        try {
          setPermission(f, permission);
          success = true;
          return os;
        } finally {
          if (!success) {
            IOUtils.cleanup(LOG, os);
          }
        }
      }

};