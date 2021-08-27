//,temp,sample_4049.java,2,15,temp,sample_5188.java,2,10
//,3
public class xxx {
private static HashMap<String, FileStatus> removeTempOrDuplicateFilesNonMm( FileStatus[] files, FileSystem fs) throws IOException {
if (files == null || fs == null) {
return null;
}
HashMap<String, FileStatus> taskIdToFile = new HashMap<String, FileStatus>();
for (FileStatus one : files) {
if (isTempPath(one)) {
Path onePath = one.getPath();


log.info("removetemporduplicatefiles deleting");
}
}
}

};