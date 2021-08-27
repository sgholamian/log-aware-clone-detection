//,temp,Context.java,468,517,temp,PathInfo.java,51,93
//,3
public class xxx {
  private Path getStagingDir(Path inputPath, boolean mkdir) {
    final URI inputPathUri = inputPath.toUri();
    final String inputPathName = inputPathUri.getPath();
    final String fileSystem = inputPathUri.getScheme() + ":" + inputPathUri.getAuthority();
    final FileSystem fs;

    try {
      fs = inputPath.getFileSystem(conf);
    } catch (IOException e) {
      throw new IllegalStateException("Error getting FileSystem for " + inputPath + ": "+ e, e);
    }

    String stagingPathName;
    if (inputPathName.indexOf(stagingDir) == -1) {
      stagingPathName = new Path(inputPathName, stagingDir).toString();
    } else {
      stagingPathName = inputPathName.substring(0, inputPathName.indexOf(stagingDir) + stagingDir.length());
    }

    final String key = fileSystem + "-" + stagingPathName + "-" + TaskRunner.getTaskRunnerID();

    Path dir = fsScratchDirs.get(key);
    if (dir == null) {
      // Append task specific info to stagingPathName, instead of creating a sub-directory.
      // This way we don't have to worry about deleting the stagingPathName separately at
      // end of query execution.
      dir = fs.makeQualified(new Path(
          stagingPathName + "_" + this.executionId + "-" + TaskRunner.getTaskRunnerID()));

      LOG.debug("Created staging dir = " + dir + " for path = " + inputPath);

      if (mkdir) {
        try {
          if (!FileUtils.mkdir(fs, dir, conf)) {
            throw new IllegalStateException("Cannot create staging directory  '" + dir.toString() + "'");
          }

          if (isHDFSCleanup) {
            fs.deleteOnExit(dir);
          }
        } catch (IOException e) {
          throw new RuntimeException("Cannot create staging directory '" + dir.toString() + "': " + e.getMessage(), e);
        }
      }

      fsScratchDirs.put(key, dir);
    }

    return dir;
  }

};