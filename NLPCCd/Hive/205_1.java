//,temp,sample_3791.java,2,14,temp,sample_1325.java,2,14
//,2
public class xxx {
public HadoopFileStatus(Configuration conf, FileSystem fs, Path file) throws IOException {
FileStatus fileStatus = fs.getFileStatus(file);
AclStatus aclStatus = null;
if (Objects.equal(conf.get("dfs.namenode.acls.enabled"), "true")) {
try {
aclStatus = fs.getAclStatus(file);
} catch (Exception e) {


log.info("skipping acl inheritance file system for path does not support acls but dfs namenode acls enabled is set to true");
}
}
}

};