//,temp,sample_4840.java,2,16,temp,sample_4841.java,2,16
//,3
public class xxx {
public void dummy_method(){
HRegionServer hrs = null;
for (RegionServerThread rs : regionServerThreads) {
if (!rs.getRegionServer().getRegions(TABLE_NAME).isEmpty()) {
hrs = rs.getRegionServer();
break;
}
}
CompactedHFilesDischarger cleaner = new CompactedHFilesDischarger(100, null, hrs, false);
cleaner.chore();
FSUtils.logFileSystemState(fs, rootDir, LOG);


log.info("running hfile cleaners");
}

};