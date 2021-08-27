//,temp,sample_1066.java,2,10,temp,sample_1067.java,2,14
//,3
public class xxx {
protected boolean pollDirectory(String fileName, List<GenericFile<File>> fileList, int depth) {
depth++;
File directory = new File(fileName);
if (!directory.exists() || !directory.isDirectory()) {
if (getEndpoint().isDirectoryMustExist()) {
throw new GenericFileOperationFailedException("Directory does not exist: " + directory);
}
return true;
}


log.info("polling directory");
}

};