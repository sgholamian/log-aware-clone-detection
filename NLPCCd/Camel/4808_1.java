//,temp,sample_7654.java,2,15,temp,sample_1506.java,2,15
//,2
public class xxx {
protected boolean doPollDirectory(String absolutePath, String dirName, List<GenericFile<SftpRemoteFile>> fileList, int depth) {
depth++;
dirName = FileUtil.stripTrailingSeparator(dirName);
String dir;
if (isStepwise()) {
dir = ObjectHelper.isNotEmpty(dirName) ? dirName : absolutePath;
operations.changeCurrentDirectory(dir);
} else {
dir = absolutePath;
}


log.info("polling directory");
}

};