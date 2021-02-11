//,temp,sample_1703.java,2,14,temp,sample_3388.java,2,9
//,3
public class xxx {
private void checkMobRegionDir(Path regionDir) throws IOException {
if (!fs.exists(regionDir)) {
return;
}
FileStatus[] hfs = null;
try {
hfs = fs.listStatus(regionDir, new FamilyDirFilter(fs));
} catch (FileNotFoundException fnfe) {


log.info("mob directory does not exist likely the table is deleted skipping");
}
}

};