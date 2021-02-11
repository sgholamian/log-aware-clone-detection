//,temp,WindowsSecureContainerExecutor.java,602,617,temp,WindowsSecureContainerExecutor.java,573,584
//,3
public class xxx {
  @Override
  protected void createDir(Path dirPath, FsPermission perms,
      boolean createParent, String owner) throws IOException {
    
    // WSCE requires dirs to be 750, not 710 as DCE.
    // This is similar to how LCE creates dirs
    //
    perms = new FsPermission(DIR_PERM);
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("createDir: %s perm:%s owner:%s", 
          dirPath.toString(), perms.toString(), owner));
    }
    
    super.createDir(dirPath, perms, createParent, owner);
    lfs.setOwner(dirPath, owner, nodeManagerGroup);
  }

};