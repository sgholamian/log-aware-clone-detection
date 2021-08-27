//,temp,sample_4801.java,2,14,temp,sample_4802.java,2,15
//,3
public class xxx {
private void removeFiles(HiveConf conf, String location, ValidTxnList txnList, Table t) throws IOException {
AcidUtils.Directory dir = AcidUtils.getAcidState(new Path(location), conf, txnList, Ref.from(false), false, t.getParameters());
List<FileStatus> abortedDirs = dir.getAbortedDirectories();
List<Path> filesToDelete = new ArrayList<>(abortedDirs.size());
for (FileStatus stat : abortedDirs) {
filesToDelete.add(stat.getPath());
}
if (filesToDelete.size() < 1) {


log.info("hmm nothing to delete in the worker for directory that hardly seems right");
}
}

};