//,temp,sample_3016.java,2,18,temp,sample_764.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (Entry<Integer, Future<ScanInfoPerBlockPool>> report : compilersInProgress.entrySet()) {
Integer index = report.getKey();
try {
dirReports[index] = report.getValue().get();
if (dirReports[index] == null) {
dirReports = null;
break;
}
} catch (Exception ex) {
FsVolumeSpi fsVolumeSpi = volumes.get(index);


log.info("error compiling report for the volume storageid");
}
}
}

};