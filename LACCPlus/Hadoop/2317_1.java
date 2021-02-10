//,temp,JobResourceUploader.java,879,900,temp,TestDFSIO.java,997,1014
//,3
public class xxx {
  private void disableErasureCodingForPath(Path path)
      throws IOException {
    try {
      if (jtFs instanceof DistributedFileSystem) {
        LOG.info("Disabling Erasure Coding for path: " + path);
        DistributedFileSystem dfs = (DistributedFileSystem) jtFs;
        dfs.setErasureCodingPolicy(path,
            SystemErasureCodingPolicies.getReplicationPolicy().getName());
      }
    } catch (RemoteException e) {
      if (!RpcNoSuchMethodException.class.getName().equals(e.getClassName())) {
        throw e;
      } else {
        if (LOG.isDebugEnabled()) {
          LOG.debug(
              "Ignore disabling erasure coding for path {} because method "
                  + "disableErasureCodingForPath doesn't exist, probably "
                  + "talking to a lower version HDFS.", path.toString(), e);
        }
      }
    }
  }

};