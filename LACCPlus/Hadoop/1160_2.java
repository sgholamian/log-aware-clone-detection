//,temp,DefaultContainerExecutor.java,533,580,temp,DockerContainerExecutor.java,777,824
//,2
public class xxx {
  protected Path getWorkingDir(List<String> localDirs, String user,
    String appId) throws IOException {
    Path appStorageDir = null;
    long totalAvailable = 0L;
    long[] availableOnDisk = new long[localDirs.size()];
    int i = 0;
    // randomly choose the app directory
    // the chance of picking a directory is proportional to
    // the available space on the directory.
    // firstly calculate the sum of all available space on these directories
    for (String localDir : localDirs) {
      Path curBase = getApplicationDir(new Path(localDir),
        user, appId);
      long space = 0L;
      try {
        space = getDiskFreeSpace(curBase);
      } catch (IOException e) {
        LOG.warn("Unable to get Free Space for " + curBase.toString(), e);
      }
      availableOnDisk[i++] = space;
      totalAvailable += space;
    }

    // throw an IOException if totalAvailable is 0.
    if (totalAvailable <= 0L) {
      throw new IOException("Not able to find a working directory for "
        + user);
    }

    // make probability to pick a directory proportional to
    // the available space on the directory.
    long randomPosition = RandomUtils.nextLong() % totalAvailable;
    int dir = 0;
    // skip zero available space directory,
    // because totalAvailable is greater than 0 and randomPosition
    // is less than totalAvailable, we can find a valid directory
    // with nonzero available space.
    while (availableOnDisk[dir] == 0L) {
      dir++;
    }
    while (randomPosition > availableOnDisk[dir]) {
      randomPosition -= availableOnDisk[dir++];
    }
    appStorageDir = getApplicationDir(new Path(localDirs.get(dir)),
      user, appId);

    return appStorageDir;
  }

};