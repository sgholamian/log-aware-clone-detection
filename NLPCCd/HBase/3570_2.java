//,temp,sample_1698.java,2,11,temp,sample_1696.java,2,11
//,2
public class xxx {
protected void checkColFamDir(Path cfDir) throws IOException {
FileStatus[] statuses = null;
try {
statuses = fs.listStatus(cfDir);
} catch (FileNotFoundException fnfe) {


log.info("colfam directory does not exist likely due to concurrent split compaction skipping");
}
}

};