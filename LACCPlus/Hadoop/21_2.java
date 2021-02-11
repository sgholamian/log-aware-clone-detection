//,temp,WindowsSecureContainerExecutor.java,618,627,temp,WindowsSecureContainerExecutor.java,591,599
//,3
public class xxx {
  @Override
  protected void copyFile(Path src, Path dst, String owner) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("copyFile: %s -> %s owner:%s", src.toString(), 
          dst.toString(), owner));
    }
    Native.Elevated.copy(src,  dst, true);
    Native.Elevated.chown(dst, owner, nodeManagerGroup);
  }

};