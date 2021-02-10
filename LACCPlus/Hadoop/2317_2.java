//,temp,JobResourceUploader.java,879,900,temp,TestDFSIO.java,997,1014
//,3
public class xxx {
  void createAndEnableECOnPath(FileSystem fs, Path path)
      throws IOException {
    String erasureCodePolicyName =
        getConf().get(ERASURE_CODE_POLICY_NAME_KEY, null);

    fs.mkdirs(path);
    Collection<ErasureCodingPolicyInfo> list =
        ((DistributedFileSystem) fs).getAllErasureCodingPolicies();
    for (ErasureCodingPolicyInfo info : list) {
      final ErasureCodingPolicy ec = info.getPolicy();
      if (erasureCodePolicyName.equals(ec.getName())) {
        ((DistributedFileSystem) fs).setErasureCodingPolicy(path, ec.getName());
        LOG.info("enable erasureCodePolicy = " + erasureCodePolicyName  +
            " on " + path.toString());
        break;
      }
    }
  }

};