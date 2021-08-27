//,temp,sample_4803.java,2,17,temp,sample_5211.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<FileStatus> abortedDirs = dir.getAbortedDirectories();
List<Path> filesToDelete = new ArrayList<>(abortedDirs.size());
for (FileStatus stat : abortedDirs) {
filesToDelete.add(stat.getPath());
}
if (filesToDelete.size() < 1) {
return;
}
FileSystem fs = filesToDelete.get(0).getFileSystem(conf);
for (Path dead : filesToDelete) {


log.info("going to delete path");
}
}

};