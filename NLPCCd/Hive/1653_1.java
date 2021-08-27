//,temp,sample_3458.java,2,18,temp,sample_3459.java,2,18
//,3
public class xxx {
public void dummy_method(){
final List<FileStatus> abortedDirectories = new ArrayList<>();
List<HdfsFileStatusWithId> childrenWithId = null;
Boolean val = useFileIds.value;
if (val == null || val) {
try {
childrenWithId = SHIMS.listLocatedHdfsStatus(fs, directory, hiddenFileFilter);
if (val == null) {
useFileIds.value = true;
}
} catch (Throwable t) {


log.info("failed to get files with id using regular api");
}
}
}

};