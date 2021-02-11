//,temp,WindowsSecureContainerExecutor.java,630,643,temp,WindowsSecureContainerExecutor.java,592,600
//,3
public class xxx {
  @Override
  public Path localizeClasspathJar(Path jarPath, Path target, String owner) 
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("localizeClasspathJar: %s %s o:%s", 
          jarPath, target, owner));
    }
    createDir(target,  new FsPermission(DIR_PERM), true, owner);
    String fileName = jarPath.getName();
    Path dst = new Path(target, fileName);
    Native.Elevated.move(jarPath, dst, true);
    Native.Elevated.chown(dst, owner, nodeManagerGroup);
    return dst;
  }

};