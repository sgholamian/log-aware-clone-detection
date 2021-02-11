//,temp,sample_1698.java,2,11,temp,sample_1696.java,2,11
//,2
public class xxx {
protected void checkMobColFamDir(Path cfDir) throws IOException {
FileStatus[] statuses = null;
try {
statuses = fs.listStatus(cfDir);
} catch (FileNotFoundException fnfe) {


log.info("mob colfam directory does not exist likely the table is deleted skipping");
}
}

};