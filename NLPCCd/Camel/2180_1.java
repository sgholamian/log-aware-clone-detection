//,temp,sample_539.java,2,17,temp,sample_538.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (endpoint.getConfiguration().isStepwise()) {
currentDir = getCurrentDirectory();
String path = FileUtil.onlyPath(name);
if (path != null) {
changeCurrentDirectory(path);
}
remoteName = FileUtil.stripPath(name);
}
if (resumeDownload && existingSize > 0) {
clientActivityListener.onResumeDownloading(endpoint.getConfiguration().remoteServerInformation(), name, existingSize);


log.info("resuming download of file at position");
}
}

};