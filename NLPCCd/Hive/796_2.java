//,temp,sample_5210.java,2,15,temp,sample_5209.java,2,14
//,3
public class xxx {
private void removeFiles(String location, ValidTxnList txnList) throws IOException {
AcidUtils.Directory dir = AcidUtils.getAcidState(new Path(location), conf, txnList);
List<FileStatus> obsoleteDirs = dir.getObsolete();
List<Path> filesToDelete = new ArrayList<Path>(obsoleteDirs.size());
for (FileStatus stat : obsoleteDirs) {
filesToDelete.add(stat.getPath());
}
if (filesToDelete.size() < 1) {


log.info("hmm nothing to delete in the cleaner for directory that hardly seems right");
}
}

};