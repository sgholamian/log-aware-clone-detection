//,temp,WindowsSecureContainerExecutor.java,352,360,temp,WindowsSecureContainerExecutor.java,343,350
//,3
public class xxx {
      @Override
      public void setPermission(Path p, FsPermission permission) 
          throws IOException {
        if (LOG.isDebugEnabled()) {
          LOG.debug(String.format("EFS:setPermission: %s %s", p, permission));
        }
        Native.Elevated.chmod(p, permission.toShort());
      }

};