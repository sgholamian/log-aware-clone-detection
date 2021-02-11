//,temp,DockerClient.java,46,60,temp,RpcProgramNfs3.java,228,241
//,3
public class xxx {
  public DockerClient(Configuration conf) throws ContainerExecutionException {

    String tmpDirBase = conf.get("hadoop.tmp.dir");
    if (tmpDirBase == null) {
      throw new ContainerExecutionException("hadoop.tmp.dir not set!");
    }
    tmpDirPath = tmpDirBase + "/nm-docker-cmds";

    File tmpDir = new File(tmpDirPath);
    if (!(tmpDir.exists() || tmpDir.mkdirs())) {
      LOG.warn("Unable to create directory: " + tmpDirPath);
      throw new ContainerExecutionException("Unable to create directory: " +
          tmpDirPath);
    }
  }

};