//,temp,sample_2423.java,2,13,temp,sample_1705.java,2,11
//,3
public class xxx {
protected void checkRegionDir(Path regionDir) throws IOException {
FileStatus[] statuses = null;
try {
statuses = fs.listStatus(regionDir);
} catch (FileNotFoundException fnfe) {


log.info("region directory does not exist likely due to concurrent split compaction skipping");
}
}

};