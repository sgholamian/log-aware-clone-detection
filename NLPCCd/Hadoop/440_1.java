//,temp,sample_8634.java,2,12,temp,sample_8632.java,2,13
//,3
public class xxx {
private void addDirectoryToSerialNumberIndex(Path serialDirPath) {
if (LOG.isDebugEnabled()) {
}
String serialPart = serialDirPath.getName();
String timestampPart = JobHistoryUtils .getTimestampPartFromPath(serialDirPath.toString());
if (timestampPart == null) {


log.info("could not find timestamp portion from path continuing with next");
}
}

};