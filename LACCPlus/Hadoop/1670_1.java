//,temp,CleanerTask.java,277,293,temp,DynamicInputChunk.java,137,142
//,3
public class xxx {
  private boolean removeResourceFromCacheFileSystem(Path path)
      throws IOException {
    // rename the directory to make the delete atomic
    Path renamedPath = new Path(path.toString() + RENAMED_SUFFIX);
    if (fs.rename(path, renamedPath)) {
      // the directory can be removed safely now
      // log the original path
      LOG.info("Deleting " + path.toString());
      return fs.delete(renamedPath, true);
    } else {
      // we were unable to remove it for some reason: it's best to leave
      // it at that
      LOG.error("We were not able to rename the directory to "
          + renamedPath.toString() + ". We will leave it intact.");
    }
    return false;
  }

};