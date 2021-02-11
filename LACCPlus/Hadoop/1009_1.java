//,temp,DockerClient.java,62,81,temp,TrafficController.java,624,648
//,3
public class xxx {
  public String writeCommandToTempFile(DockerCommand cmd, String filePrefix)
      throws ContainerExecutionException {
    File dockerCommandFile = null;
    try {
      dockerCommandFile = File.createTempFile(TMP_FILE_PREFIX + filePrefix,
          TMP_FILE_SUFFIX, new
          File(tmpDirPath));

      Writer writer = new OutputStreamWriter(new FileOutputStream(dockerCommandFile),
          "UTF-8");
      PrintWriter printWriter = new PrintWriter(writer);
      printWriter.print(cmd.getCommandWithArguments());
      printWriter.close();

      return dockerCommandFile.getAbsolutePath();
    } catch (IOException e) {
      LOG.warn("Unable to write docker command to temporary file!");
      throw new ContainerExecutionException(e);
    }
  }

};