//,temp,sample_5227.java,2,17,temp,sample_5228.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
URI jarUri = FileUtils.getURI(addedJar);
if (jarUri != null && !localJars.contains(jarUri)) {
localJars.add(jarUri);
if (SparkUtilities.needUploadToHDFS(jarUri, sparkConf)) {
jarUri = SparkUtilities.uploadToHDFS(jarUri, hiveConf);
}
remoteClient.addJar(jarUri);
}
} catch (URISyntaxException e) {


log.info("failed to add jar");
}
}

};