//,temp,DefaultContainerExecutor.java,472,493,temp,DockerContainerExecutor.java,455,476
//,2
public class xxx {
  @Override
  public void deleteAsUser(DeletionAsUserContext ctx)
      throws IOException, InterruptedException {
    Path subDir = ctx.getSubDir();
    List<Path> baseDirs = ctx.getBasedirs();

    if (baseDirs == null || baseDirs.size() == 0) {
      LOG.info("Deleting absolute path : " + subDir);
      if (!lfs.delete(subDir, true)) {
        //Maybe retry
        LOG.warn("delete returned false for path: [" + subDir + "]");
      }
      return;
    }
    for (Path baseDir : baseDirs) {
      Path del = subDir == null ? baseDir : new Path(baseDir, subDir);
      LOG.info("Deleting path : " + del);
      if (!lfs.delete(del, true)) {
        LOG.warn("delete returned false for path: [" + del + "]");
      }
    }
  }

};