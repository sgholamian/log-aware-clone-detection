//,temp,sample_134.java,2,13,temp,sample_3308.java,2,10
//,3
public class xxx {
private boolean isFileClosed(final DistributedFileSystem dfs, final Method m, final Path p) {
try {
return (Boolean) m.invoke(dfs, p);
} catch (SecurityException e) {


log.info("no access");
}
}

};