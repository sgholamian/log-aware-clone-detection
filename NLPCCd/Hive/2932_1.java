//,temp,sample_3895.java,2,18,temp,sample_3896.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (this.ioCxtRef.getCurrentBlockStart() == 0) {
footerBuffer = null;
Path filePath = this.ioCxtRef.getInputPath();
PartitionDesc part = null;
try {
if (pathToPartitionInfo == null) {
pathToPartitionInfo = Utilities .getMapWork(jobConf).getPathToPartitionInfo();
}
part = HiveFileFormatUtils .getFromPathRecursively(pathToPartitionInfo, filePath, IOPrepareCache.get().getPartitionDescMap());
} catch (AssertionError ae) {


log.info("cannot get partition description from because");
}
}
}

};