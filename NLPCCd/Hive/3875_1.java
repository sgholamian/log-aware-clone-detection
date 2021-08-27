//,temp,sample_4051.java,2,10,temp,sample_4050.java,2,8
//,3
public class xxx {
private static FileStatus compareTempOrDuplicateFiles(FileSystem fs, FileStatus file, FileStatus existingFile) throws IOException {
FileStatus toDelete = null, toRetain = null;
Path filePath = file.getPath();
if (isCopyFile(filePath.getName())) {


log.info("file identified as duplicate this file is not deleted as it has copysuffix");
}
}

};