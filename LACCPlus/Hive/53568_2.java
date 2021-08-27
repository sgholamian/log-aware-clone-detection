//,temp,Context.java,468,517,temp,PathInfo.java,51,93
//,3
public class xxx {
  Path computeStagingDir(Path inputPath) {
    final URI inputPathUri = inputPath.toUri();
    final String inputPathName = inputPathUri.getPath();
    final String fileSystemAsString = inputPathUri.getScheme() + ":" + inputPathUri.getAuthority();

    String stagingPathName;
    if (!inputPathName.contains(stagingDir)) {
      stagingPathName = new Path(inputPathName, stagingDir).toString();
    } else {
      stagingPathName =
          inputPathName.substring(0, inputPathName.indexOf(stagingDir) + stagingDir.length());
    }

    final String key =
        fileSystemAsString + "-" + stagingPathName + "-" + TaskRunner.getTaskRunnerID();

    Path dir = fsScratchDirs.get(key);
    try {
      FileSystem fileSystem = inputPath.getFileSystem(hiveConf);
      if (dir == null) {
        // Append task specific info to stagingPathName, instead of creating a sub-directory.
        // This way we don't have to worry about deleting the stagingPathName separately at
        // end of query execution.
        Path path = new Path(
            stagingPathName + "_" + generateExecutionId() + "-" + TaskRunner.getTaskRunnerID());
        dir = fileSystem.makeQualified(path);

        LOG.debug("Created staging dir = " + dir + " for path = " + inputPath);

        if (!FileUtils.mkdir(fileSystem, dir, hiveConf)) {
          throw new IllegalStateException(
              "Cannot create staging directory  '" + dir.toString() + "'");
        }
        fileSystem.deleteOnExit(dir);
      }
      fsScratchDirs.put(key, dir);
      return dir;

    } catch (IOException e) {
      throw new RuntimeException(
          "Cannot create staging directory '" + dir.toString() + "': " + e.getMessage(), e);
    }
  }

};