//,temp,sample_8634.java,2,12,temp,sample_8632.java,2,13
//,3
public class xxx {
private void removeDirectoryFromSerialNumberIndex(Path serialDirPath) {
String serialPart = serialDirPath.getName();
String timeStampPart = JobHistoryUtils .getTimestampPartFromPath(serialDirPath.toString());
if (timeStampPart == null) {
return;
}
if (serialPart == null) {


log.info("could not find serial portion from path continuing with next");
}
}

};