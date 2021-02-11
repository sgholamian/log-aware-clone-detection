//,temp,CleanerTask.java,277,293,temp,DynamicInputChunk.java,137,142
//,3
public class xxx {
  public void assignTo(TaskID taskId) throws IOException {
    Path newPath = new Path(chunkRootPath, taskId.toString());
    if (!fs.rename(chunkFilePath, newPath)) {
      LOG.warn(chunkFilePath + " could not be assigned to " + taskId);
    }
  }

};