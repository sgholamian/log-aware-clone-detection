//,temp,RetriableFileCopyCommand.java,227,235,temp,CopyCommitter.java,117,130
//,3
public class xxx {
  private Path getTmpFile(Path target, Mapper.Context context) {
    Path targetWorkPath = new Path(context.getConfiguration().
        get(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH));

    Path root = target.equals(targetWorkPath)? targetWorkPath.getParent() : targetWorkPath;
    LOG.info("Creating temp file: " +
        new Path(root, ".distcp.tmp." + context.getTaskAttemptID().toString()));
    return new Path(root, ".distcp.tmp." + context.getTaskAttemptID().toString());
  }

};