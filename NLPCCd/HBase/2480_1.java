//,temp,sample_577.java,2,14,temp,sample_1252.java,2,13
//,3
public class xxx {
public Path bulkLoadHFile(byte[] family, String srcPathStr, Path dstPath) throws IOException {
Path srcPath = new Path(srcPathStr);
try {
fs.commitStoreFile(srcPath, dstPath);
} finally {
if (this.getCoprocessorHost() != null) {
this.getCoprocessorHost().postCommitStoreFile(family, srcPath, dstPath);
}
}


log.info("loaded hfile into store as updating store file list");
}

};