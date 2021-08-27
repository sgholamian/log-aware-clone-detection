//,temp,RemoteHiveSparkClient.java,282,297,temp,RemoteHiveSparkClient.java,264,279
//,2
public class xxx {
  private void addResources(String addedFiles) throws IOException {
    for (String addedFile : CSV_SPLITTER.split(Strings.nullToEmpty(addedFiles))) {
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
        LOG.warn("Failed to add file:" + addedFile, e);
      }
    }
  }

};