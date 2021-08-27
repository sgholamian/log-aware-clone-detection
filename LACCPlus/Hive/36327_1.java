//,temp,RemoteHiveSparkClient.java,282,297,temp,RemoteHiveSparkClient.java,264,279
//,2
public class xxx {
  private void addJars(String addedJars) throws IOException {
    for (String addedJar : CSV_SPLITTER.split(Strings.nullToEmpty(addedJars))) {
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
        LOG.warn("Failed to add jar:" + addedJar, e);
      }
    }
  }

};