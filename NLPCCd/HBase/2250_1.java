//,temp,sample_2410.java,2,11,temp,sample_1064.java,2,10
//,3
public class xxx {
public static void waitOnSafeMode(final Configuration conf, final long wait) throws IOException {
FileSystem fs = FileSystem.get(conf);
if (!(fs instanceof DistributedFileSystem)) return;
DistributedFileSystem dfs = (DistributedFileSystem)fs;
while (isInSafeMode(dfs)) {


log.info("waiting for dfs to exit safe mode");
}
}

};