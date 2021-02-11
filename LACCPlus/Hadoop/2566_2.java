//,temp,S3AFileSystem.java,501,505,temp,PathOutputCommitter.java,71,76
//,3
public class xxx {
  protected PathOutputCommitter(Path outputPath,
      JobContext context) throws IOException {
    this.context = Preconditions.checkNotNull(context, "Null context");
    LOG.debug("Creating committer with output path {} and job context"
        + " {}", outputPath, context);
  }

};