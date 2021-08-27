//,temp,sample_3792.java,2,14,temp,sample_1326.java,2,14
//,2
public class xxx {
public HadoopFileStatus(Configuration conf, FileSystem fs, Path file) throws IOException {
FileStatus fileStatus = fs.getFileStatus(file);
AclStatus aclStatus = null;
if (Objects.equal(conf.get("dfs.namenode.acls.enabled"), "true")) {
try {
aclStatus = fs.getAclStatus(file);
} catch (Exception e) {


log.info("the details are");
}
}
}

};