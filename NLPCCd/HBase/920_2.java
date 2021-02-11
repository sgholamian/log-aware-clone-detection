//,temp,sample_3165.java,2,16,temp,sample_4056.java,2,16
//,2
public class xxx {
private void recoverLease(final Configuration conf, final Path path) {
try {
final FileSystem dfs = FSUtils.getCurrentFileSystem(conf);
FSUtils fsUtils = FSUtils.getInstance(dfs, conf);
fsUtils.recoverFileLease(dfs, path, conf, new CancelableProgressable() {
public boolean progress() {
return true;
}
});
} catch (IOException e) {


log.info("unable to recover lease for wal");
}
}

};