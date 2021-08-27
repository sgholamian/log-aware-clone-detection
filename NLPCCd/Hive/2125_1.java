//,temp,sample_5227.java,2,17,temp,sample_5228.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
URI fileUri = FileUtils.getURI(addedFile);
if (fileUri != null && !localFiles.contains(fileUri)) {
localFiles.add(fileUri);
if (SparkUtilities.needUploadToHDFS(fileUri, sparkConf)) {
fileUri = SparkUtilities.uploadToHDFS(fileUri, hiveConf);
}
remoteClient.addFile(fileUri);
}
} catch (URISyntaxException e) {


log.info("failed to add file");
}
}

};