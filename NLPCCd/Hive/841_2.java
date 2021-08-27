//,temp,sample_2462.java,2,12,temp,sample_2461.java,2,10
//,3
public class xxx {
private void checkPartitionDirs(Path basePath, Set<Path> allDirs, final List<String> partColNames) throws IOException, HiveException {
int poolSize = conf.getInt(ConfVars.METASTORE_FS_HANDLER_THREADS_COUNT.varname, 15);
ExecutorService executor;
if (poolSize <= 1) {


log.info("using single threaded version of msck getpaths");
}
}

};